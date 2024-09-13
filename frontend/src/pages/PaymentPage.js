import React, { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import api from '../services/api';

const PaymentPage = () => {
  const { orderId } = useParams();  // Retrieve the orderId from the URL
  const [paymentMethod, setPaymentMethod] = useState('credit_card');
  const [cardDetails, setCardDetails] = useState({ cardNumber: '', expiry: '', cvv: '' });
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [paymentMessage, setPaymentMessage] = useState('');
  const navigate = useNavigate();
  const token = localStorage.getItem('token');

  const handlePayment = async () => {
    setLoading(true);
    setError('');
    try {
      // Send payment data to the backend
      const response = await api.post(`/api/payment/${orderId}`, {
        paymentMethod,
        cardDetails
      }, {
        headers: { Authorization: `Bearer ${token}` }
      });

      console.log('Payment successful:', response.data);

      setPaymentMessage('Payment successful! Redirecting to order history...');

      setTimeout(() => {
        navigate('/order-history');  // Redirect to order history after successful payment
      }, 3000);
    } catch (err) {
      setError('Payment failed: ' + (err.response?.data?.message || err.message || 'Unknown error occurred.'));
    }
    setLoading(false);
  };

  return (
    <div>
      <h1>Payment</h1>
      {loading && <p>Processing payment...</p>}
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {paymentMessage && <p style={{ color: 'green' }}>{paymentMessage}</p>}

      <div>
        <label>Payment Method:</label>
        <select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
          <option value="credit_card">Credit Card</option>
          <option value="paypal">PayPal</option>
          {/* Add more payment options here */}
        </select>
      </div>

      {paymentMethod === 'credit_card' && (
        <div>
          <label>Card Number:</label>
          <input
            type="text"
            value={cardDetails.cardNumber}
            onChange={(e) => setCardDetails({ ...cardDetails, cardNumber: e.target.value })}
          />
          <label>Expiry Date:</label>
          <input
            type="text"
            value={cardDetails.expiry}
            onChange={(e) => setCardDetails({ ...cardDetails, expiry: e.target.value })}
          />
          <label>CVV:</label>
          <input
            type="text"
            value={cardDetails.cvv}
            onChange={(e) => setCardDetails({ ...cardDetails, cvv: e.target.value })}
          />
        </div>
      )}

      <button onClick={handlePayment}>Make Payment</button>
    </div>
  );
};

export default PaymentPage;
