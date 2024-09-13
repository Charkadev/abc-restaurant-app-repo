import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import './CheckoutPage.css';

const CheckoutPage = () => {
  const [cart, setCart] = useState(null);
  const [deliveryAddress, setDeliveryAddress] = useState('');
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

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
        setError('Failed to load cart: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
      }
      setLoading(false);
    };
    fetchCart();
  }, [token]);

  const handleProceedToPayment = async () => {
    if (!deliveryAddress.trim()) {
      setError('Please provide a delivery address');
      return;
    }

    setLoading(true);
    setError('');
    try {
      // Create a temporary order before proceeding to payment
      const response = await api.post('/api/order/prepare', {
        deliveryAddress
      }, {
        headers: { Authorization: `Bearer ${token}` }
      });

      console.log('Order prepared:', response.data);

      // Redirect to the payment page with the orderId
      navigate(`/payment/${response.data.orderId}`);
    } catch (err) {
      setError('Failed to proceed to payment: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
    }
    setLoading(false);
  };

  if (loading) return <p>Loading...</p>;
  if (error) return <p style={{ color: 'red' }}>{error}</p>;
  if (!cart || cart.items.length === 0) return <p>Your cart is empty.</p>;

  return (
    <div>
      <h1>Checkout</h1>
      <ul>
        {cart.items.map(item => (
          <li key={item.id}>
            {item.food.item_name} - {item.quantity} x ${item.food.price} = ${item.totalPrice}
          </li>
        ))}
      </ul>
      <h2>Total: ${cart.total}</h2>
      <div>
        <label>Delivery Address:</label>
        <input
          type="text"
          value={deliveryAddress}
          onChange={(e) => setDeliveryAddress(e.target.value)}
          placeholder="Enter your delivery address"
        />
      </div>
      <button onClick={handleProceedToPayment}>Proceed to Payment</button>
    </div>
  );
};

export default CheckoutPage;
