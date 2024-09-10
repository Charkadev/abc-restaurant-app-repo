import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';  // Axios instance for API calls
import './MenuPage.css';

const MenuPage = () => {
  const [foods, setFoods] = useState([]);
  const [search, setSearch] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  
  const token = localStorage.getItem('token'); // Check if the user is logged in

  // Fetch all foods or search for foods based on search term
  useEffect(() => {
    const fetchFoods = async () => {
      setLoading(true);
      setError('');
      try {
        const response = await api.get(`/api/food/search?name=${search}`);
        setFoods(response.data);
      } catch (err) {
        console.error('Error fetching foods:', err);
        setError('Failed to fetch food items');
      }
      setLoading(false);
    };

    fetchFoods();
  }, [search]);

  // Handle adding food to the cart
  const handleAddToCart = (foodId) => {
    if (!token) {
      // If the user is not logged in, navigate to the login page
      navigate('/login');
      return;
    }

    // If the user is logged in, add the food item to the cart
    api.put(`/api/cart/add`, { foodId })
      .then(() => alert('Food item added to cart'))
      .catch((err) => {
        console.error('Error adding to cart:', err);
        alert('Failed to add item to cart. Please try again.');
      });
  };

  return (
    <div className="menu-page">
      <h1>Menu</h1>

      {/* Search bar for searching food items */}
      <input
        type="text"
        placeholder="Search for food..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="search-bar"
      />

      {/* Display loading indicator or error message */}
      {loading ? (
        <p>Loading...</p>
      ) : error ? (
        <p>{error}</p>
      ) : (
        <div className="food-grid">
          {foods.length > 0 ? (
            foods.map((food) => (
              <div key={food.id} className="food-item">
                <img src={food.imageUrl} alt={food.name} />
                <h3>{food.name}</h3>
                <p>{food.description}</p>
                <p>${food.price.toFixed(2)}</p>
                <button onClick={() => handleAddToCart(food.id)}>Add to Cart</button>
              </div>
            ))
          ) : (
            <p>No food items found.</p>
          )}
        </div>
      )}
    </div>
  );
};

export default MenuPage;
