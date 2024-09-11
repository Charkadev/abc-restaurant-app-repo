import React, { useEffect, useState } from 'react';
import api from '../services/api';  // Axios instance for API calls

const ManageRestaurants = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [newRestaurant, setNewRestaurant] = useState({ name: '', location: '', cuisineType: '', description: '' });
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await api.get('/api/admin/restaurants');
        setRestaurants(response.data);
      } catch (error) {
        console.error('Error fetching restaurants:', error);
      }
    };

    fetchRestaurants();
  }, []);

  const handleCreateRestaurant = async () => {
    try {
      await api.post('/api/admin/restaurants', newRestaurant);
      setNewRestaurant({ name: '', location: '', cuisineType: '', description: '' });
      const response = await api.get('/api/admin/restaurants');
      setRestaurants(response.data);
    } catch (error) {
      console.error('Error creating restaurant:', error);
    }
  };

  const handleUpdateRestaurant = async () => {
    try {
      await api.put(`/api/admin/restaurants/${selectedRestaurant.id}`, selectedRestaurant);
      setSelectedRestaurant(null);
      const response = await api.get('/api/admin/restaurants');
      setRestaurants(response.data);
    } catch (error) {
      console.error('Error updating restaurant:', error);
    }
  };

  const handleDeleteRestaurant = async (id) => {
    try {
      await api.delete(`/api/admin/restaurants/${id}`);
      const response = await api.get('/api/admin/restaurants');
      setRestaurants(response.data);
    } catch (error) {
      console.error('Error deleting restaurant:', error);
    }
  };

  return (
    <div>
      <h2>Manage Restaurants</h2>

      {/* Form to create a new restaurant */}
      <div>
        <h3>Create New Restaurant</h3>
        <input
          type="text"
          placeholder="Name"
          value={newRestaurant.name}
          onChange={(e) => setNewRestaurant({ ...newRestaurant, name: e.target.value })}
        />
        <input
          type="text"
          placeholder="Location"
          value={newRestaurant.location}
          onChange={(e) => setNewRestaurant({ ...newRestaurant, location: e.target.value })}
        />
        <input
          type="text"
          placeholder="Cuisine Type"
          value={newRestaurant.cuisineType}
          onChange={(e) => setNewRestaurant({ ...newRestaurant, cuisineType: e.target.value })}
        />
        <input
          type="text"
          placeholder="Description"
          value={newRestaurant.description}
          onChange={(e) => setNewRestaurant({ ...newRestaurant, description: e.target.value })}
        />
        <button onClick={handleCreateRestaurant}>Create Restaurant</button>
      </div>

      {/* List of restaurants with edit and delete options */}
      <h3>Restaurants List</h3>
      <ul>
        {restaurants.map(restaurant => (
          <li key={restaurant.id}>
            {restaurant.name} ({restaurant.cuisineType}) - {restaurant.location}
            <button onClick={() => setSelectedRestaurant(restaurant)}>Edit</button>
            <button onClick={() => handleDeleteRestaurant(restaurant.id)}>Delete</button>
          </li>
        ))}
      </ul>

      {/* Form to update a selected restaurant */}
      {selectedRestaurant && (
        <div>
          <h3>Update Restaurant</h3>
          <input
            type="text"
            placeholder="Name"
            value={selectedRestaurant.name}
            onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, name: e.target.value })}
          />
          <input
            type="text"
            placeholder="Location"
            value={selectedRestaurant.location}
            onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, location: e.target.value })}
          />
          <input
            type="text"
            placeholder="Cuisine Type"
            value={selectedRestaurant.cuisineType}
            onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, cuisineType: e.target.value })}
          />
          <input
            type="text"
            placeholder="Description"
            value={selectedRestaurant.description}
            onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, description: e.target.value })}
          />
          <button onClick={handleUpdateRestaurant}>Update Restaurant</button>
        </div>
      )}
    </div>
  );
};

export default ManageRestaurants;
