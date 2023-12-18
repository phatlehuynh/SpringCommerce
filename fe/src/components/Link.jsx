
import React, { useState } from 'react';

const Link = () => {
  const [email, setEmail] = useState('');

  const handleRegisterClick = async () => {
    try {
      // Gọi API sử dụng fetch hoặc Axios
      console.log('Before API call');
      const response = await fetch(`https://localhost:7076/api/users/sendLinkRegister?toMail=${email}`, {
        method: 'GET',
      });
      console.log('Response Status:', response.status);
      // Xử lý kết quả từ API
      if (response.ok) {
        console.log('API call successful');
        alert('gửi mail thành công, hãy kiểm tra mail để nhận link đăng kí');
        // Thực hiện các hành động khác nếu cần
      } else {
        console.error('API call failed');
      }
    } catch (error) {
      console.error('Error during API call:', error);
      // Xử lý lỗi nếu cần
    }
  };

  return (
    <div className='grid grid-cols-1 md:grid-cols-1 h-screen w-full'>
      <div className='bg-gray-100 flex flex-col justify-center'>
        <div className='max-w-[400px] w-full mx-auto bg-white p-4'>
          <h2 className='text-4xl font-bold text-center py-6'>KONAN TUNE</h2>
          <div className='flex flex-col py-2'>
            <label>Email</label>
            <input
              className='border p-2'
              type='email'
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />
          </div>
          <button
            className='border w-full my-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white'
            onClick={handleRegisterClick}
          >
            Register
          </button>
          <div className='flex justify-between'>
            <p className='flex items-center'>
              <input className='mr-2' type='checkbox' /> Remember Me
            </p>
            <p>
              Already have an account? Login
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Link;

