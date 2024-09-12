import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
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
        const response = await api.get(search ? `/api/food/search?name=${search}` : `/api/food/all`);
        setFoods(response.data);
      } catch (err) {
        console.error('Error fetching foods:', err);
        setError('Failed to fetch food items');
      }
      setLoading(false);
    };
    fetchFoods();
  }, [search]);

  const handleAddToCart = (foodId) => {
    if (!token) {
      navigate('/login');
      return;
    }

    api.put(`/api/cart/add`, { foodId })
      .then(() => alert('Food item added to cart'))
      .catch((err) => {
        console.error('Error adding to cart:', err);
        alert('Failed to add item to cart.');
      });
  };

  return (
    <div className="menu-page">
      <h1>Menu</h1>
      <input
        type="text"
        placeholder="Search for food..."
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        className="search-bar"
      />
      {loading ? <p>Loading...</p> : error ? <p>{error}</p> : (
        <div className="food-grid">
          {foods.length > 0 ? (
            foods.map((food) => (
              <div key={food.id} className="food-item">
                <img src={food.image_url} alt={food.item_name} />
                <h3>{food.item_name}</h3>
                <p>{food.item_description}</p>
                <p><strong>Category:</strong> {food.category}</p>
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
