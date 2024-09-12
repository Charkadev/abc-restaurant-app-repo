import React, { useState, useEffect } from 'react';
import api from '../services/api';

const ManageMenu = () => {
  const [menuItems, setMenuItems] = useState([]);
  const [newItem, setNewItem] = useState({ name: '', description: '', price: 0, available: true });

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

  const handleCreateMenuItem = async () => {
    try {
      await api.post('/api/food/create', newItem);
      setNewItem({ name: '', description: '', price: 0, available: true });
      // Fetch the updated list of menu items
      const response = await api.get('/api/food/all');
      setMenuItems(response.data);
    } catch (error) {
      console.error('Error creating menu item:', error);
    }
  };

  const handleDeleteMenuItem = async (id) => {
    try {
      await api.delete(`/api/food/delete/${id}`);
      // Fetch the updated list of menu items
      const response = await api.get('/api/food/all');
      setMenuItems(response.data);
    } catch (error) {
      console.error('Error deleting menu item:', error);
    }
  };

  return (
    <div>
      <h2>Manage Menu Items</h2>

      {/* Form to create a new menu item */}
      <div>
        <h3>Create New Menu Item</h3>
        <input
          type="text"
          placeholder="Name"
          value={newItem.name}
          onChange={(e) => setNewItem({ ...newItem, name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newItem.description}
          onChange={(e) => setNewItem({ ...newItem, description: e.target.value })}
        />
        <input
          type="number"
          placeholder="Price"
          value={newItem.price}
          onChange={(e) => setNewItem({ ...newItem, price: parseFloat(e.target.value) })}
        />
        <label>
          <input
            type="checkbox"
            checked={newItem.available}
            onChange={(e) => setNewItem({ ...newItem, available: e.target.checked })}
          />
          Available
        </label>
        <button onClick={handleCreateMenuItem}>Create Menu Item</button>
      </div>

      {/* List of menu items */}
      <h3>Menu Items List</h3>
      <ul>
        {menuItems.map(item => (
          <li key={item.id}>
            {item.name} - ${item.price}
            <button onClick={() => handleDeleteMenuItem(item.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ManageMenu;
