import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

const ChangePasswordForm = () => {
  const navigate = useNavigate();
  const { token } = useParams();
  const [newPassword, setNewPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');

  const handleChangePasswordClick = async () => {
    console.log(token);
    console.log(newPassword);
    if (newPassword !== confirmPassword) {
      alert('Passwords do not match. Please re-enter.');
      return;
    }
    try {
      const response = await fetch(`https://localhost:7076/api/users/ChangePass?token=${token}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(newPassword),
      });

      if (response.ok) {
        console.log('Password change successful');
        alert('Đổi mật khẩu thành công');
        navigate("/login");
      } else {
        const error = await response.json();
        alert(`Failed to change password: ${error.message}`);
        console.error('Password change failed');
      }
    } catch (error) {
      console.error('Error during password change:', error);
      // Handle the error as needed
    }
  };

  return (
    <div className='max-w-[400px] w-full mx-auto bg-white p-4'>
      <h2 className='text-4xl font-bold text-center py-6'>Change Password</h2>
      <div className='flex flex-col py-2'>
        <label>New Password</label>
        <input
          className='border p-2'
          type='password'
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}
        />
      </div>
      <div className='flex flex-col py-2'>
        <label>Confirm Password</label>
        <input
          className='border p-2'
          type='password'
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}
        />
      </div>
      <button
        className='border w-full my-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white'
        onClick={handleChangePasswordClick}
      >
        Change Password
      </button>
    </div>
  );
};
export default ChangePasswordForm;
