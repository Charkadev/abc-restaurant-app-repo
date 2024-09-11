import React, { useEffect, useState } from 'react';
import api from '../services/api';  // Axios instance for API calls

const ManageUsers = () => {
  const [users, setUsers] = useState([]);
  const [newUser, setNewUser] = useState({
    fullName: '',
    email: '',
    role: 'ROLE_CUSTOMER',  // Default role, matching the backend USER_ROLE enum
    password: ''
  });
  const [selectedRole, setSelectedRole] = useState('ROLE_CUSTOMER');  // State for dropdown
  const [editingUserId, setEditingUserId] = useState(null);  // To track if updating a user

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await api.get('/api/admin/users');
        setUsers(response.data);
      } catch (error) {
        console.error('Error fetching users:', error);
      }
    };

    fetchUsers();
  }, []);

  // Handle creating a new user or updating an existing one
  const handleSaveUser = async () => {
    try {
      if (editingUserId) {
        // Update user if an ID is set
        const userToUpdate = { ...newUser, role: selectedRole };
        await api.put(`/api/admin/users/${editingUserId}`, userToUpdate);
        setEditingUserId(null);  // Reset after update
      } else {
        // Create new user
        const userToCreate = { ...newUser, role: selectedRole };  // Use selected role
        await api.post('/api/admin/users', userToCreate);
      }

      setNewUser({ fullName: '', email: '', role: 'ROLE_CUSTOMER', password: '' });
      setSelectedRole('ROLE_CUSTOMER');
      const response = await api.get('/api/admin/users');
      setUsers(response.data);
    } catch (error) {
      console.error(`Error ${editingUserId ? 'updating' : 'creating'} user:`, error);
    }
  };

  // Handle role change from dropdown
  const handleRoleChange = (e) => {
    setSelectedRole(e.target.value);  // Update selected role from dropdown
  };

  // Handle edit button click, populate form with user data
  const handleEditUser = (user) => {
    setEditingUserId(user.id);  // Set the ID of the user being edited
    setNewUser({
      fullName: user.fullName,
      email: user.email,
      role: user.role,
      password: ''  // Leave password blank for updates, update only if provided
    });
    setSelectedRole(user.role);  // Set the role in the dropdown
  };

  // Handle delete user
  const handleDeleteUser = async (id) => {
    try {
      await api.delete(`/api/admin/users/${id}`);
      const response = await api.get('/api/admin/users');
      setUsers(response.data);
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
    <div>
      <h2>Manage Users</h2>

      {/* Form to create or update a user */}
      <div>
        <h3>{editingUserId ? 'Update User' : 'Create New User'}</h3>
        <input
          type="text"
          placeholder="Full Name"
          value={newUser.fullName}
          onChange={(e) => setNewUser({ ...newUser, fullName: e.target.value })}
        />
        <input
          type="email"
          placeholder="Email"
          value={newUser.email}
          onChange={(e) => setNewUser({ ...newUser, email: e.target.value })}
        />
        <input
          type="password"
          placeholder="Password"
          value={newUser.password}
          onChange={(e) => setNewUser({ ...newUser, password: e.target.value })}
        />
        
        {/* Dropdown for selecting role */}
        <select value={selectedRole} onChange={handleRoleChange}>
          <option value="ROLE_ADMIN">Admin</option>
          <option value="ROLE_RESTAURANT_STAFF">Restaurant Staff</option>
          <option value="ROLE_CUSTOMER">Customer</option>
        </select>

        <button onClick={handleSaveUser}>
          {editingUserId ? 'Update User' : 'Create User'}
        </button>
      </div>

      {/* List of users with Edit/Delete options */}
      <h3>Users List</h3>
      <ul>
        {users.map(user => (
          <li key={user.id}>
            {user.fullName} ({user.email}) - {user.role}
            <button onClick={() => handleEditUser(user)}>Edit</button>
            <button onClick={() => handleDeleteUser(user.id)}>Delete</button>
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ManageUsers;
