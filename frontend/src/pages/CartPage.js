import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './CartPage.css';  // Assuming you have some basic styles for the cart page

const CartPage = () => {
  const [cartItems, setCartItems] = useState([]);
  const [total, setTotal] = useState(0);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch cart items
    const fetchCartItems = async () => {
      try {
        const response = await api.get('/api/cart');  // Fetch from backend
        setCartItems(response.data.items);
        setTotal(response.data.total);
      } catch (err) {
        setError('Failed to load cart.');
      }
    };

    fetchCartItems();
  }, []);

  // Calculate total price after removing an item
  const calculateTotal = (items) => {
    const totalPrice = items.reduce((sum, item) => sum + item.price * item.quantity, 0);
    setTotal(totalPrice);
  };

  // Handle removing an item from the cart
  const handleRemoveItem = async (itemId) => {
    try {
      await api.delete(`/api/cart/${itemId}`);  // Call backend to remove item
      const updatedItems = cartItems.filter((item) => item.id !== itemId);
      setCartItems(updatedItems);
      calculateTotal(updatedItems);
    } catch (err) {
      setError('Failed to remove item.');
    }
  };

  // Handle checkout process
  const handleCheckout = () => {
    navigate('/payment');  // Redirect to payment page
  };

  return (
    <div className="cart-page">
      <h2>Shopping Cart</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {cartItems.length === 0 ? (
        <p>Your cart is empty.</p>
      ) : (
        <div>
          <ul>
            {cartItems.map((item) => (
              <li key={item.id} className="cart-item">
                <h3>{item.name}</h3>
                <p>Price: ${item.price.toFixed(2)}</p>
                <p>Quantity: {item.quantity}</p>
                <button onClick={() => handleRemoveItem(item.id)}>Remove</button>
              </li>
            ))}
          </ul>
          <h3>Total: ${total.toFixed(2)}</h3>
          <button onClick={handleCheckout}>Proceed to Checkout</button>
        </div>
      )}
    </div>
  );
};

export default CartPage;
