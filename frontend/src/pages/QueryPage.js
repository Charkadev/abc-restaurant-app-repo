import React, { useState } from 'react';
import api from '../services/api';

const QueryPage = () => {
  const [query, setQuery] = useState('');
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await api.post('/api/queries', { message: query }); // Replace with your backend endpoint
      setSuccess('Query submitted successfully!');
      setQuery('');
    } catch (err) {
      setError('Failed to submit query.');
    }
  };

  return (
    <div>
      <h2>Submit a Query</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      {success && <p style={{ color: 'green' }}>{success}</p>}
      <form onSubmit={handleSubmit}>
        <textarea
          value={query}
          onChange={(e) => setQuery(e.target.value)}
          placeholder="Enter your query here"
          required
        />
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default QueryPage;
