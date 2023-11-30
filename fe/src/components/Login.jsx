import React, { useState } from 'react';
import { useParams, useNavigate} from 'react-router-dom';
import { Link } from 'react-router-dom';

const Login = () => {
  const navigate = useNavigate();
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  const handleLoginClick = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch('http://localhost:8080/api/login', {
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
        const data = await response.json();

        // Lưu thông tin người dùng vào local storage
        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));
        console.log('Login successful');
        window.dispatchEvent(new Event('storage'));

        navigate('/',{ replace: true });
        // Thực hiện các hành động khác nếu cần
      } else {
        console.error('Login failed');
        const data = await response.json();
       alert(`${data.message} Sai tên đăng nhập hoặc mật khẩu`);
      }
    } catch (error) {
      console.error('Error during login:', error);
      // Xử lý lỗi nếu cần
    }
  };

  return (
    <div className='grid grid-cols-1 md:grid-cols-1 h-screen w-full'>
      <div className='bg-gray-100 flex flex-col justify-center'>
        <form className='max-w-[400px] w-full mx-auto bg-white p-4' onSubmit={handleLoginClick}>
          <h2 className='text-4xl font-bold text-center py-6'>BRAND.</h2>
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
            type='submit'
            className='border w-full my-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white'
            onClick={handleLoginClick}
          >
            Sign In
          </button>

          <div className='flex justify-between'>
          <Link to="/linkChangePass">
          <p className='flex items-center'>
              <input className='mr-2' type='checkbox' /> Quên mật khẩu ?
            </p>
          </Link>
          <Link to="/link"><p>Create an account</p></Link>
          </div>
        </form>
      </div>
    </div>
  );
};
export default Login;
