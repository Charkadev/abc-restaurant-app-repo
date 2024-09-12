import React, { useState, useEffect } from 'react';
import api from '../services/api';

const RestaurantsPage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch all restaurants without requiring authorization
    const fetchRestaurants = async () => {
      try {
        const response = await api.get('/api/restaurants');  // Fetch restaurants without token
        setRestaurants(response.data);
      } catch (err) {
        setError('Failed to load restaurants.');
      }
    };

    fetchRestaurants();
  }, []);

  if (error) return <p>{error}</p>;

  return (
    <div>
      <h2>Available Restaurants</h2>
      {restaurants.length === 0 ? (
        <p>No restaurants available.</p>
      ) : (
        <ul>
          {restaurants.map((restaurant) => (
            <li key={restaurant.id}>
              <h3>{restaurant.name}</h3>
              <p>{restaurant.description}</p>
              <p>Location: {restaurant.location}</p>
              <p>Contact: {restaurant.contact}</p>
              <p>Address: {restaurant.address}</p>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RestaurantsPage;
