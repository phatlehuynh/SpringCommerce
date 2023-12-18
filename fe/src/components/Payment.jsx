import React from 'react';
import { useLocation } from 'react-router-dom';

const Payment = () => {
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);

  const vnp_Amount = searchParams.get('vnp_Amount');
  const vnp_BankCode = searchParams.get('vnp_BankCode');
  const vnp_BankTranNo = searchParams.get('vnp_BankTranNo');

  return (
    <div>
      <h1> trang thanh toan</h1>
      <h1>Payment Response Component</h1>
      <p>Amount: {vnp_Amount}</p>
      <p>Bank Code: {vnp_BankCode}</p>
      <p>Bank Transaction No: {vnp_BankTranNo}</p>
      {/* ... Hiển thị các thông tin khác */}
    </div>
  );
};

export default Payment;
