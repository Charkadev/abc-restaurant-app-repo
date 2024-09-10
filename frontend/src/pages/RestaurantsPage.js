import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../services/api';

const RestaurantsPage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    // Fetch all restaurants
    const fetchRestaurants = async () => {
      try {
        const response = await api.get('/api/restaurants');  // API call for restaurants
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
              <Link to={`/menu/${restaurant.id}`}>View Menu</Link>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RestaurantsPage;
