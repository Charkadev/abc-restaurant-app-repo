import React, { useEffect, useState } from 'react';
import api from '../services/api';

const ManageRestaurants = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [newRestaurant, setNewRestaurant] = useState({
    name: '', location: '', description: '', contact: '', address: ''
  });
  const [selectedRestaurant, setSelectedRestaurant] = useState(null);
  const [error, setError] = useState('');  // Error state for general error
  const [loading, setLoading] = useState(true);  // Loading state

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        console.log('Fetching restaurants...');
        const response = await api.get('/api/staff/restaurants');
        console.log('API Response:', response.data);  // Log the response for troubleshooting
        setRestaurants(response.data);
        setError('');  // Clear any previous error
      } catch (error) {
        console.error('Error fetching restaurants:', error);
        if (error.response) {
          // Log the response data and status
          console.error('Response data:', error.response.data);
          console.error('Response status:', error.response.status);
          setError(`Error fetching restaurants: ${error.response.data.message || 'Unknown error occurred'}`);
        } else {
          setError('Failed to load restaurants. Please check your network or API server.');
        }
      } finally {
        setLoading(false);  // Turn off the loading state
      }
    };

    fetchRestaurants();
  }, []);

  const handleCreateRestaurant = async () => {
    try {
      console.log('Creating new restaurant:', newRestaurant);
      await api.post('/api/staff/restaurants', newRestaurant);
      setNewRestaurant({
        name: '', location: '', description: '', contact: '', address: ''
      });
      const response = await api.get('/api/staff/restaurants');
      console.log('Updated restaurant list after creation:', response.data); // Log the updated list
      setRestaurants(response.data);
      setError('');
    } catch (error) {
      console.error('Error creating restaurant:', error);
      setError('Failed to create a new restaurant. Please try again.');
    }
  };

  const handleUpdateRestaurant = async () => {
    if (!selectedRestaurant) {
      setError('No restaurant selected for update.');
      return;
    }
    try {
      console.log('Updating restaurant:', selectedRestaurant);
      await api.put(`/api/staff/restaurants/${selectedRestaurant.id}`, selectedRestaurant);
      setSelectedRestaurant(null);
      const response = await api.get('/api/staff/restaurants');
      console.log('Updated restaurant list after update:', response.data);  // Log the updated list
      setRestaurants(response.data);
      setError('');
    } catch (error) {
      console.error('Error updating restaurant:', error);
      setError('Failed to update the restaurant. Please try again.');
    }
  };

  const handleDeleteRestaurant = async (id) => {
    try {
      console.log('Deleting restaurant with ID:', id);
      await api.delete(`/api/staff/restaurants/${id}`);
      const response = await api.get('/api/staff/restaurants');
      console.log('Updated restaurant list after deletion:', response.data);  // Log the updated list
      setRestaurants(response.data);
      setError('');
    } catch (error) {
      console.error('Error deleting restaurant:', error);
      setError('Failed to delete the restaurant. Please try again.');
    }
  };

  // Log the restaurant state before rendering
  console.log('Current restaurants:', restaurants);

  return (
    <div>
      <h2>Manage Restaurants</h2>

      {/* Display error if any */}
      {error && <p style={{ color: 'red' }}>{error}</p>}

      {/* Display loading message while fetching data */}
      {loading ? (
        <p>Loading restaurants...</p>
      ) : (
        <>
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
              placeholder="Description"
              value={newRestaurant.description}
              onChange={(e) => setNewRestaurant({ ...newRestaurant, description: e.target.value })}
            />
            <input
              type="text"
              placeholder="Contact"
              value={newRestaurant.contact}
              onChange={(e) => setNewRestaurant({ ...newRestaurant, contact: e.target.value })}
            />
            <input
              type="text"
              placeholder="Address"
              value={newRestaurant.address}
              onChange={(e) => setNewRestaurant({ ...newRestaurant, address: e.target.value })}
            />
            <button onClick={handleCreateRestaurant}>Create Restaurant</button>
          </div>

          {/* List of restaurants with edit and delete options */}
          <h3>Restaurants List</h3>
          {restaurants.length > 0 ? (
            <ul>
              {restaurants.map(restaurant => (
                <li key={restaurant.id}>
                  {restaurant.name} - {restaurant.location}
                  <button onClick={() => setSelectedRestaurant(restaurant)}>Edit</button>
                  <button onClick={() => handleDeleteRestaurant(restaurant.id)}>Delete</button>
                </li>
              ))}
            </ul>
          ) : (
            <p>No restaurants available.</p>
          )}

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
                placeholder="Description"
                value={selectedRestaurant.description}
                onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, description: e.target.value })}
              />
              <input
                type="text"
                placeholder="Contact"
                value={selectedRestaurant.contact}
                onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, contact: e.target.value })}
              />
              <input
                type="text"
                placeholder="Address"
                value={selectedRestaurant.address}
                onChange={(e) => setSelectedRestaurant({ ...selectedRestaurant, address: e.target.value })}
              />
              <button onClick={handleUpdateRestaurant}>Update Restaurant</button>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default ManageRestaurants;
