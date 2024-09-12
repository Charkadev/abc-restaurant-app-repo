import React, { useState, useEffect } from 'react';
import api from '../services/api';

const ManageMenu = () => {
  const [menuItems, setMenuItems] = useState([]);
  const [newItem, setNewItem] = useState({ item_name: '', item_description: '', price: 0, category: '', image_url: '' });
  const [editMode, setEditMode] = useState(false); // Track if we're editing an item
  const [editItemId, setEditItemId] = useState(null); // Track which item is being edited

  // Fetch the menu items when the component mounts
  useEffect(() => {
    const fetchMenuItems = async () => {
      try {
        const response = await api.get('/api/food/all');
        setMenuItems(response.data);
      } catch (error) {
        console.error('Error fetching menu items:', error);
      }
    };

    fetchMenuItems();
  }, []);

  // Handle the creation of a new menu item
  const handleCreateMenuItem = async () => {
    try {
      await api.post('/api/food/create', newItem);
      setNewItem({ item_name: '', item_description: '', price: 0, category: '', image_url: '' });
      const response = await api.get('/api/food/all');
      setMenuItems(response.data);
    } catch (error) {
      console.error('Error creating menu item:', error);
    }
  };

  // Handle the update of an existing menu item
  const handleUpdateMenuItem = async () => {
    try {
      await api.put(`/api/food/update/${editItemId}`, newItem);
      setNewItem({ item_name: '', item_description: '', price: 0, category: '', image_url: '' });
      setEditMode(false);
      setEditItemId(null);
      const response = await api.get('/api/food/all');
      setMenuItems(response.data);
    } catch (error) {
      console.error('Error updating menu item:', error);
    }
  };

  // Handle deleting a menu item
  const handleDeleteMenuItem = async (id) => {
    try {
      await api.delete(`/api/food/delete/${id}`);
      const response = await api.get('/api/food/all');
      setMenuItems(response.data);
    } catch (error) {
      console.error('Error deleting menu item:', error);
    }
  };

  // Handle editing a menu item
  const handleEditMenuItem = (item) => {
    setEditMode(true);
    setEditItemId(item.id);
    setNewItem({
      item_name: item.item_name,
      item_description: item.item_description,
      price: item.price,
      category: item.category,
      image_url: item.image_url,
    });
  };

  return (
    <div>
      <h2>Manage Menu Items</h2>

      {/* Form to create or update a menu item */}
      <div>
        <h3>{editMode ? 'Update Menu Item' : 'Create New Menu Item'}</h3>
        <input
          type="text"
          placeholder="Item Name"
          value={newItem.item_name}
          onChange={(e) => setNewItem({ ...newItem, item_name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newItem.item_description}
          onChange={(e) => setNewItem({ ...newItem, item_description: e.target.value })}
        />
        <input
          type="number"
          placeholder="Price"
          value={newItem.price}
          onChange={(e) => setNewItem({ ...newItem, price: parseFloat(e.target.value) })}
        />
        <input
          type="text"
          placeholder="Category"
          value={newItem.category}
          onChange={(e) => setNewItem({ ...newItem, category: e.target.value })}
        />
        <input
          type="text"
          placeholder="Image URL"
          value={newItem.image_url}
          onChange={(e) => setNewItem({ ...newItem, image_url: e.target.value })}
        />
        <button onClick={editMode ? handleUpdateMenuItem : handleCreateMenuItem}>
          {editMode ? 'Update Menu Item' : 'Create Menu Item'}
        </button>
        {editMode && <button onClick={() => { setEditMode(false); setNewItem({ item_name: '', item_description: '', price: 0, category: '', image_url: '' }); }}>Cancel Edit</button>}
      </div>

      {/* List of menu items */}
      <h3>Menu Items List</h3>
      <ul>
        {menuItems.map(item => (
          <li key={item.id}>
            {item.item_name} - ${item.price}
            <button onClick={() => handleEditMenuItem(item)}>Edit</button> {/* Edit Button */}
            <button onClick={() => handleDeleteMenuItem(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ManageMenu;
