import React from 'react';

const PaymentPage = () => {
  const handlePayment = () => {
    // Trigger the Stripe payment process here
    alert('Payment successful!');
  };

  return (
    <div>
      <h2>Payment</h2>
      <button onClick={handlePayment}>Make Payment</button>
    </div>
  );
};

export default PaymentPage;

