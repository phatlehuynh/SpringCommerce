import React, { useState, useEffect } from 'react';

const BillList = () => {
  const [bills, setBills] = useState([]);
  const userId = JSON.parse(localStorage.getItem('user')).id;

  useEffect(() => {
    // Lấy token từ Local Storage
    const token = localStorage.getItem('token');
  
    // Kiểm tra xem token có tồn tại không
    if (!token) {
      console.error('Token not found. User may not be authenticated.');
      return;
    }
  
    // Tạo các tùy chọn cho yêu cầu fetch
    const requestOptions = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    };
  
    // Gọi API để lấy dữ liệu hóa đơn
    fetch(`http://localhost:8080/api/order/getByUserId?userId=${userId}`, requestOptions)
      .then(response => response.json())
      .then(data => {
        setBills(data.data);
      })
      .catch(error => console.error('Error fetching bill data:', error));
  }, [userId]);

  if (bills.length === 0) {
    // Loading state, you can customize this part
    return (
      <div style={{ textAlign: 'center', marginTop: '50px' }}>
        <h1 style={{ fontWeight: 'bold' }}>Không có hóa đơn nào.</h1>
      </div>
    );
  }

  return (
    <div className="bg-gray-100">
      <div className="container mx-auto mt-10">
        <div className="flex shadow-md my-10">
          {/* Bill List Section */}
          <div className="w-3/4 bg-white px-10 py-10">
            <div className="border-b pb-8">
              <h1 className="font-semibold text-2xl">Danh sách hóa đơn</h1>
            </div>
            <div className="flex mt-10 mb-5">
            <h3 className="font-semibold text-gray-600 text-xs uppercase w-2/5">OrderId</h3>
            <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5">Ngày đặt hàng</h3>
            <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5">Tổng giá</h3>
            <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5">Trạng Thái</h3>
            <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5">Địa chỉ</h3>
          </div>

          {/* Hiển thị danh sách hóa đơn */}
          {bills.map(bill => (
            <div key={bill.id} className="flex items-center hover:bg-gray-100 -mx-8 px-6 py-5">
              <div className="text-xs w-2/5">
                <span className="text-sm">{bill.id}</span>
              </div>
              <div className="text-xs w-1/5 text-center">
                <span className="text-sm">{new Date(bill.orderDate).toLocaleString()}</span>
              </div>
              <div className="text-xs w-1/5 text-center">
                <span className="text-sm">{bill.totalPrice}</span>
              </div>
              <div className="text-xs w-1/5 text-center">
                <span className="text-sm">{bill.status === 1 ? 'Đã thanh toán' : 'Chưa thanh toán'}</span>
              </div>
              <div className="text-xs w-1/5 text-center">
                <span className="text-sm">{bill.address}</span>
              </div>
            </div>
          ))}



          </div>
        </div>
      </div>
    </div>
  );
};

export default BillList;
