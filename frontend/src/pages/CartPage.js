import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api'; // Assume this handles API calls
import './CartPage.css';

const CartPage = () => {
  const [cart, setCart] = useState(null); // Cart state to store cart data
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  // Fetch cart data on page load
  useEffect(() => {
    const fetchCart = async () => {
      setLoading(true);
      setError('');
      try {
        const cartResponse = await api.get('/api/cart', {
          headers: { Authorization: `Bearer ${token}` }
        });
        setCart(cartResponse.data);
      } catch (err) {
        console.error('Error fetching cart:', err.response?.data || err.message);
        setError('Failed to load cart data: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
      }
      setLoading(false);
    };

    fetchCart();
  }, [token]);

  const handleQuantityChange = async (cartItemId, newQuantity) => {
    try {
      const response = await api.put('/api/cart-item/update', { cartItemId, quantity: newQuantity }, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setCart(prevCart => ({
        ...prevCart,
        items: prevCart.items.map(item => item.id === cartItemId ? response.data : item)
      }));
    } catch (err) {
      console.error('Error updating item quantity:', err.response?.data || err.message);
      setError('Failed to update item: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
    }
  };

  const handleRemoveItem = async (cartItemId) => {
    try {
      await api.delete(`/api/cart-item/${cartItemId}/remove`, {
        headers: { Authorization: `Bearer ${token}` }
      });
      setCart(prevCart => ({
        ...prevCart,
        items: prevCart.items.filter(item => item.id !== cartItemId)
      }));
    } catch (err) {
      console.error('Error removing item:', err.response?.data || err.message);
      setError('Failed to remove item: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
    }
  };

  const handleCheckout = () => {
    navigate('/checkout'); // Redirect to checkout page
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!cart || cart.items.length === 0) return <p>Your cart is empty.</p>;

  return (
    <div>
      <h1>Your Cart</h1>
      <ul>
        {cart.items.map(item => (
          <li key={item.id}>
            {item.food.item_name} - ${item.food.price}
            <input
              type="number"
              min="1"
              value={item.quantity}
              onChange={(e) => handleQuantityChange(item.id, parseInt(e.target.value))}
            />
            <button onClick={() => handleRemoveItem(item.id)}>Remove</button>
            <p>Total: ${item.totalPrice}</p>
          </li>
        ))}
      </ul>
      <h2>Total: ${cart.total}</h2>
      <button onClick={handleCheckout}>Proceed to Checkout</button>
    </div>
  );
};

export default CartPage;
