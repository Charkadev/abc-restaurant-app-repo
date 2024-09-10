import React from 'react';
import { useNavigate } from 'react-router-dom';

const OrderConfirmationPage = () => {
  const navigate = useNavigate();

  const goToOrderHistory = () => {
    navigate('/order-history');
  };

  return (
    <div>
      <h2>Thank you for your order!</h2>
      <p>Your order has been placed successfully.</p>
      <button onClick={goToOrderHistory}>View Order History</button>
    </div>
  );
};

export default OrderConfirmationPage;
