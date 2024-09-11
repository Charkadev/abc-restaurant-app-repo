import React from 'react';
import { Navigate } from 'react-router-dom';

const PrivateRoute = ({ children, role }) => {
  const token = localStorage.getItem('token');
  const userRole = localStorage.getItem('role');

  // Check if the token exists and the role matches
  if (!token || userRole !== role) {
    return <Navigate to="/login" />;
  }

  return children;
};

export default PrivateRoute;
