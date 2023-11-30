import React, { useState } from 'react';

const Link = () => {
  const [email, setEmail] = useState('');
  const [isValidEmail, setIsValidEmail] = useState(true);

  const handleRegisterClick = async () => {
    // Email validation using a simple regex pattern
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const isEmailValid = emailRegex.test(email);

    if (!isEmailValid) {
      setIsValidEmail(false);
      return;
    }
    
    try {
      // Gọi API sử dụng fetch hoặc Axios
      console.log('Before API call');
      const response = await fetch(`https://localhost:7076/api/users/sendLinkChangePass?toMail=${email}`, {
        method: 'GET',
      });
      console.log('Response Status:', response.status);
      // Xử lý kết quả từ API
      if (response.ok) {
        console.log('API call successful');
        alert('gửi mail thành công, hãy kiểm tra mail để nhận link Đổi Mật khẩu');
        // Thực hiện các hành động khác nếu cần
      } else {
        const errorData = await response.json(); // Parse the JSON response
        alert(`${errorData.message}`);
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
              className={`border p-2 ${!isValidEmail ? 'border-red-500' : ''}`}
              type='email'
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setIsValidEmail(true); // Reset validation when input changes
              }}
            />
            {!isValidEmail && <p className='text-red-500'>Invalid email format</p>}
          </div>
          <button
            className='border w-full my-5 py-2 bg-indigo-600 hover:bg-indigo-500 text-white'
            onClick={handleRegisterClick}
          >
            Register
          </button>
        </div>
      </div>
    </div>
  );
};

export default Link;