import React, { useState, useEffect } from 'react';
import api from '../services/api';
import './RestaurantsPage.css';  // Import the CSS file

const RestaurantsPage = () => {
  const [restaurants, setRestaurants] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await api.get('/api/restaurants');
        setRestaurants(response.data);
      } catch (err) {
        setError('Failed to load restaurants.');
      }
    };

    fetchRestaurants();
  }, []);

  if (error) return <p>{error}</p>;

  return (
    <div className="container">
      <h2>Available Restaurants</h2>
      {restaurants.length === 0 ? (
        <p>No restaurants available.</p>
      ) : (
        <ul>
          {restaurants.map((restaurant) => (
            <li key={restaurant.id} className="restaurant-item">
              <img 
                src={restaurant.imageUrl} // Assuming the API provides an image URL for each restaurant
                alt={restaurant.name} 
                className="restaurant-image"
              />
              <div className="restaurant-details">
                <h3>{restaurant.name}</h3>
                <p>{restaurant.description}</p>
                <p>Location: {restaurant.location}</p>
                <p>Contact: {restaurant.contact}</p>
                <p>Address: {restaurant.address}</p>
              </div>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default RestaurantsPage;
