import React, { useState, useEffect } from 'react';
import api from '../services/api';

const ProfilePage = () => {
  const [profile, setProfile] = useState({});
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [updatedName, setUpdatedName] = useState('');
  const [updatedEmail, setUpdatedEmail] = useState('');

  useEffect(() => {
    // Fetch profile data on component mount
    const fetchProfile = async () => {
      try {
        const response = await api.get('/api/user/profile');  // Adjust this endpoint based on your backend
        setProfile(response.data);
        setUpdatedName(response.data.fullName);
        setUpdatedEmail(response.data.email);
      } catch (err) {
        setError('Failed to load profile.');
      } finally {
        setLoading(false);
      }
    };

    fetchProfile();
  }, []);

  const handleProfileUpdate = async (e) => {
    e.preventDefault();

    try {
      const response = await api.put('/api/user/profile', { fullName: updatedName, email: updatedEmail });
      setProfile(response.data);
      setError('');  // Clear any error on success
    } catch (err) {
      setError('Failed to update profile.');
    }
  };

  if (loading) return <p>Loading...</p>;

  return (
    <div>
      <h2>My Profile</h2>
      {error && <p style={{ color: 'red' }}>{error}</p>}
      <form onSubmit={handleProfileUpdate}>
        <label>
          Full Name:
          <input
            type="text"
            value={updatedName}
            onChange={(e) => setUpdatedName(e.target.value)}
          />
        </label>
        <label>
          Email:
          <input
            type="email"
            value={updatedEmail}
            onChange={(e) => setUpdatedEmail(e.target.value)}
          />
        </label>
        <button type="submit">Update Profile</button>
      </form>
    </div>
  );
};

export default ProfilePage;
