import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [total, setTotal] = useState(0);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch cart items
    const fetchCartItems = async () => {
      try {
        const response = await api.get('/api/cart');  // Adjust this endpoint based on your backend
        setCartItems(response.data.items);
        setTotal(response.data.total);
      } catch (err) {
        setError('Failed to load cart.');
      }
    };

    fetchCartItems();
  }, []);

  const handleCheckout = () => {
    navigate('/payment');
  };

  const handleRemoveItem = async (itemId) => {
    try {
      await api.delete(`/api/cart/${itemId}`);
      setCartItems(cartItems.filter((item) => item.id !== itemId));
    } catch (err) {
      setError('Failed to remove item.');
    }
  };

  return (
    <div>
      <h2>Shopping Cart</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div>
          <ul>
            {cartItems.map((item) => (
              <li key={item.id}>
                <h3>{item.name}</h3>
                <p>Price: ${item.price}</p>
                <p>Quantity: {item.quantity}</p>
                <button onClick={() => handleRemoveItem(item.id)}>Remove</button>
              </li>
            ))}
          </ul>
          <h3>Total: ${total}</h3>
          <button onClick={handleCheckout}>Proceed to Checkout</button>
        </div>
      )}
    </div>
  );
};

export default CartPage;
