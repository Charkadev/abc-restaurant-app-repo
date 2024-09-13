import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './MenuPage.css';

const MenuPage = () => {
  const [foods, setFoods] = useState([]);
  const [searchResults, setSearchResults] = useState([]);  // Store search results
  const [cart, setCart] = useState([]);  // Cart state to check if an item is in the cart
  const [search, setSearch] = useState('');  // For searching by name
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [cartMessage, setCartMessage] = useState('');
  const [quantities, setQuantities] = useState({});  // Store quantities for each food item
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  // Fetch all foods initially
  useEffect(() => {
    const fetchFoods = async () => {
      setLoading(true);
      setError('');
      try {
        // Fetch all food items
        const foodResponse = await api.get('/api/food/all');
        setFoods(foodResponse.data);

        // Fetch cart items to know which ones are already added to the cart
        if (token) {
          const cartResponse = await api.get('/api/cart', {
            headers: { Authorization: `Bearer ${token}` }
          });
          setCart(cartResponse.data.items || []);

          // Set initial quantities based on cart items
          const initialQuantities = {};
          cartResponse.data.items.forEach(item => {
            initialQuantities[item.food.id] = item.quantity;
          });
          setQuantities(initialQuantities);
        }
      } catch (err) {
        console.error('Error fetching foods or cart:', err.response?.data || err.message);
        setError('Failed to fetch data: ' + (err.response?.data?.message || err.message || 'An unknown error occurred.'));
      }
      setLoading(false);
    };

    fetchFoods();
  }, [token]);

  // Handle search functionality
  const handleSearch = async () => {
    if (search.trim() === '') {
      setSearchResults([]);  // Clear search results if no search term is provided
      return;
    }

    setLoading(true);
    setError('');
    try {
      // Perform search query by food name (using 'query' parameter)
      const searchResponse = await api.get(`/api/food/search?query=${encodeURIComponent(search)}`);
      setSearchResults(searchResponse.data);  // Store search results separately
    } catch (err) {
      console.error('Error searching foods:', err.response?.data || err.message);
      setError('Failed to search data: ' + (err.response?.data?.message || err.message || 'An unknown error occurred.'));
    }
    setLoading(false);
  };

  // Handle quantity input change
  const handleQuantityChange = (foodId, quantity) => {
    setQuantities({
      ...quantities,
      [foodId]: quantity
    });
  };

  const handleAddToCart = async (foodId) => {
    if (!token) {
      navigate('/login');  // Redirect to login if no token is present
      return;
    }

    const quantity = quantities[foodId] || 1;  // Default to 1 if no quantity is selected

    try {
      const response = await api.put('/api/cart/add', { foodId, quantity }, {
        headers: { Authorization: `Bearer ${token}` },  // Send token for authorization
      });
      setCart([...cart, response.data]);  // Add the new cart item to the cart state
      setCartMessage('Item added to cart successfully');
      setTimeout(() => setCartMessage(''), 3000);
    } catch (err) {
      console.error('Error adding to cart:', err.response?.data || err.message);
      setError('Failed to add item to cart: ' + (err.response?.data?.message || err.message || 'An unknown error occurred.'));
    }
  };

  const handleRemoveFromCart = async (foodId) => {
    if (!token) {
      navigate('/login');  // Redirect to login if no token is present
      return;
    }

    try {
      const cartItem = cart.find(item => item.food.id === foodId);
      if (!cartItem) {
        setError('Cart item not found');
        return;
      }

      await api.delete(`/api/cart-item/${cartItem.id}/remove`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      setCart(cart.filter(item => item.id !== cartItem.id));  // Remove the item from the cart state
      setCartMessage('Item removed from cart');
      setTimeout(() => setCartMessage(''), 3000);
    } catch (err) {
      console.error('Error removing from cart:', err.response?.data || err.message);
      setError('Failed to remove item from cart: ' + (err.response?.data?.message || err.message || 'An unknown error occurred.'));
    }
  };

  const isInCart = (foodId) => {
    return cart.some(item => item.food.id === foodId);
  };

  return (
    <div>
      <h1>Menu</h1>
      {loading && <p>Loading...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {cartMessage && <p style={{ color: 'green' }}>{cartMessage}</p>}

      {/* Search bar for food items */}
      <input
        type="text"
        value={search}
        onChange={(e) => setSearch(e.target.value)}
        placeholder="Search for food items by name"
      />
      <button onClick={handleSearch}>Search</button>

      {/* Display search results if available */}
      {searchResults.length > 0 && (
        <div>
          <h2>Search Results</h2>
          <ul>
            {searchResults.map(item => (
              <li key={item.id}>
                {item.item_name} - ${item.price}
                {isInCart(item.id) ? (
                  <div>
                    <input
                      type="number"
                      min="1"
                      value={quantities[item.id] || 1}
                      onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                    />
                    <button onClick={() => handleRemoveFromCart(item.id)}>Remove from Cart</button>
                  </div>
                ) : (
                  <div>
                    <input
                      type="number"
                      min="1"
                      value={quantities[item.id] || 1}
                      onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                    />
                    <button onClick={() => handleAddToCart(item.id)}>Add to Cart</button>
                  </div>
                )}
              </li>
            ))}
          </ul>
        </div>
      )}

      {/* Display full menu list regardless of search */}
      <div>
        <h2>All Menu Items</h2>
        <ul>
          {foods.map(item => (
            <li key={item.id}>
              {item.item_name} - ${item.price}
              {isInCart(item.id) ? (
                <div>
                  <input
                    type="number"
                    min="1"
                    value={quantities[item.id] || 1}
                    onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                  />
                  <button onClick={() => handleRemoveFromCart(item.id)}>Remove from Cart</button>
                </div>
              ) : (
                <div>
                  <input
                    type="number"
                    min="1"
                    value={quantities[item.id] || 1}
                    onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
                  />
                  <button onClick={() => handleAddToCart(item.id)}>Add to Cart</button>
                </div>
              )}
            </li>
          ))}
        </ul>
      </div>
    </div>
  );
};

export default MenuPage;
