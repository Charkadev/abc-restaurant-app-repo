import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const response = await api.post('/auth/signin', { email, password });
      localStorage.setItem('token', response.data.jwt);
      localStorage.setItem('role', response.data.role);

      const userRole = response.data.role;
      
      // Redirect based on the role after successful login
      if (userRole === 'ROLE_ADMIN') {
        navigate('/dashboard/admin');
      } else if (userRole === 'ROLE_RESTAURANT_STAFF') {
        navigate('/dashboard/staff');
      } else if (userRole === 'ROLE_CUSTOMER') {
        navigate('/dashboard/customer');
      }
    } catch (err) {
      setError('Login failed. Please check your credentials.');
    }
  };

  return (
    <div>
      <h2>Login</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleLogin}>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <button type="submit">Login</button>
      </form>
    </div>
  );
};

export default LoginPage;
