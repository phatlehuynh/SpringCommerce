import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
const RegisterForm = () => {
  const navigate = useNavigate();
  const { email } = useParams();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleRegisterClick = async () => {
    try {
      const response = await fetch(`https://localhost:7076/api/users/Register?email=${email}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          username: username,
          password: password,
        }),
      });
      if (response.ok) {
        console.log('Registration successful');
        alert(`Đăng kí thành công`);
        navigate("/login");
      } else {
        const error = await response.json();
        alert(`Failed to add product: ${error.message}`);
        console.error('Registration failed');
        // Xử lý lỗi nếu cần
      }
    } catch (error) {
      console.error('Error during registration:', error);
      // Xử lý lỗi nếu cần
    }
  };

  useEffect(() => {
    // Cập nhật giá trị email từ URL nếu có
    if (email) {
      // Bạn có thể thực hiện các xử lý khác nếu cần
      console.log(`Email from URL: ${email}`);
    }
  }, [email]);

  return (
    <div className='max-w-[400px] w-full mx-auto bg-white p-4'>
      <h2 className='text-4xl font-bold text-center py-6'>Register</h2>
      <div className='flex flex-col py-2'>
        <label>Email</label>
        <input
          className='border p-2'
          type='email'
          value={email}
          readOnly // Không cho phép người dùng chỉnh sửa giá trị email
        />
      </div>
      <div className='flex flex-col py-2'>
        <label>Username</label>
        <input
          className='border p-2'
          type='text'
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
      </div>
      <div className='flex flex-col py-2'>
        <label>Password</label>
        <input
          className='border p-2'
          type='password'
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
      </div>
      <button
        className='border w-full my-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white'
        onClick={handleRegisterClick}
      >
        Register
      </button>
    </div>
  );
};

export default RegisterForm;
