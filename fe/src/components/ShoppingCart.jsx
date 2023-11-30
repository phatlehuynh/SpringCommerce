
import React, { useState,useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import { v4 as uuidv4, validate as uuidValidate, parse as uuidParse } from 'uuid';


const ShoppingCart = () => {
  const navigate = useNavigate();
  const [order, setOrder] = useState(null);
  const location = useLocation();
  const searchParams = new URLSearchParams(location.search);
  const vnpResponseCode = searchParams.get('vnp_ResponseCode');
  const [address, setAddress] = useState('');
  let totalPrice = 0;

  // Lấy thông tin người dùng từ localStorage
  const userData = JSON.parse(localStorage.getItem('user'));


  const handleAddProductToOrder = () => {

  }


  const fetchCartData = async () => {
    try {

      // Kiểm tra xem userId có tồn tại không
      if (!userData || !userData.id) {
        console.error('User ID not found in Local Storage');
        return;
      }

      // Lấy token từ localStorage
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

      console.log(userData)

      // Thực hiện yêu cầu fetch để lấy dữ liệu đơn hàng
      const response = await fetch(`http://localhost:8080/api/cart/${userData.cartId}`, requestOptions);

      if (response.ok) {
        // Chuyển đổi dữ liệu JSON từ phản hồi
        const data = await response.json();
        console.log("-----------------------------------------")
        console.log(data)
        // Cập nhật state với dữ liệu đơn hàng
        setOrder(data.data);
      } else {
        console.error('Error fetching order data:', response.statusText);
      }
    } catch (error) {
      console.error('Error fetching order data:', error);
    }
  };

  const handleChangeQuantity = async(productId, quantity) => {
    try {
      // Send a POST request to the API to add the product to the cart
      const response = await fetch(`http://localhost:8080/api/cart/addProductToCart?cartId=${userData.cartId}&productId=${productId}&quantity=${quantity}`, {
        method: 'PUT'
      });
  
      if (response.ok) {
        alert('Thêm sản phẩm thành công');
            // Fetch updated order
        await fetchCartData();

        // Add any additional logic you need for a successful response
        // Redirect to ProductAdmin or other page after a successful update
      } else {
        const error = await response.json();
        console.error('Thêm sản phẩm thất bại', error);
        alert(`Thêm sản phẩm thất bại: ${error.message}`);
        // Handle error scenarios here
      }
    } catch (error) {
      console.error('Error:', error.message);
      alert(`Error: ${error.message}`);
    }
  }

  useEffect(() => {

    // Gọi hàm để lấy dữ liệu

    fetchCartData();
  }, [vnpResponseCode]);
  
    if (!order) {
      // Loading state, you can customize this part
      return (
        <div style={{ textAlign: 'center', marginTop: '50px' }}>
          <img src="https://drive.gianhangvn.com/image/empty-cart.jpg" alt="Empty Cart"  className="h-40 w-40 object-cover rounded mx-auto" />
          <h1 style={{ fontWeight: 'bold', marginTop: '10px'}} className='text-5xl h-60 '>Giỏ hàng trống</h1>
        </div>
      );
    }

    const handlePay = async () => {
      if (!address) {
        alert('Vui lòng nhập địa chỉ');
        return;
      }
      try {
          // Tạo dữ liệu gửi đi cho API
          const payload = {
              orderType: 'string', // Thay thế bằng logic xác định loại đơn hàng thực tế
              amount: order.totalPrice,
              orderDescription: 'string', // Thay thế bằng logic mô tả đơn hàng thực tế
              name: 'string', // Thay thế bằng logic xác định tên người đặt hàng thực tế
          };
          console.log(payload);
          // Gọi API để tạo đơn hàng
          const response = await fetch('https://localhost:7076/api/Order/create', {
              method: 'POST',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify(payload),
          });
  
          if (response.ok) {
            localStorage.setItem('address', address);
              // Phân tích JSON response
              const responseData = await response.json();
  
              // Trích xuất URL thanh toán từ response
              const paymentUrl = responseData.paymentUrl;
  
              // Chuyển hướng người dùng đến URL thanh toán
              window.location.href = paymentUrl;
          } else {
              console.error('Thanh toán thất bại');
          }
      } catch (error) {
          console.error('Lỗi:', error);
      }
  };
  const handleCheckout = async () => {
    // Kiểm tra xem địa chỉ đã được nhập chưa
    if (!address) {
      alert('Vui lòng nhập địa chỉ');
      return;
    }
    // Lấy userId từ Local Storage
    try {
      console.log(userData);
  
      const payload = {
        address: address,
        status: 1,
        userId: userData.id, 
        cartId: userData.cartId
      };
      console.log(payload.cartId)
      const url = `http://localhost:8080/api/order/insert`;
  
      // Gửi request để xóa sản phẩm khỏi giỏ hàng
      const response = await fetch(url, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(
          payload
        ),
      });
  
      if (response.ok) {
        // Xử lý nếu xóa khỏi giỏ hàng thành công
        alert('Thanh toán thành công!');
        fetchCartData();
        // Cập nhật lại danh sách đơn hàng sau khi xóa
        
      } else {
        // Xử lý nếu có lỗi khi xóa khỏi giỏ hàng
        const error = await response.json();
        alert(`Failed to insert order: ${error.message}`);
      }
    } catch (error) {
      console.error('Error removing product from cart:', error);
    }
  };
  const handleRemoveFromCart = async (productId) => {
    try {
      const token = localStorage.getItem('token');
      const userData = localStorage.getItem('user');
      const user = JSON.parse(userData);
      const userId = user.id;
      console.log(userId);
      console.log(productId);
    
      // Gửi request để xóa sản phẩm khỏi giỏ hàng
      const response = await fetch(`http://localhost:8080/api/cart/removeProductFromCart?cartId=${user.cartId}&productId=${productId}`, {
        method: 'DELETE', // Sử dụng POST thay vì DELETE
        });
  
      if (response.ok) {
        // Xử lý nếu xóa khỏi giỏ hàng thành công
        alert('Product removed from cart successfully!');
        fetchCartData();
        // Cập nhật lại danh sách đơn hàng sau khi xóa
        
      } else {
        // Xử lý nếu có lỗi khi xóa khỏi giỏ hàng
        const error = await response.json();
        alert(`Failed to remove product from cart: ${error.message}`);
      }
    } catch (error) {
      console.error('Error removing product from cart:', error);
    }
  };
  


  return (
    <div className="bg-gray-100">
      <div className="container mx-auto mt-10">
        <div className="flex shadow-md my-10">
          {/* Product List Section */}
          <div className="w-3/4 bg-white px-10 py-10">
            <div className="flex justify-between border-b pb-8">
              <h1 className="font-semibold text-2xl">Shopping Cart</h1>
              <h2 className="font-semibold text-2xl">3 Items</h2>
            </div>
            <div className="flex mt-10 mb-5">
              <h3 className="font-semibold text-gray-600 text-xs uppercase w-2/5">Product Details</h3>
              <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Quantity</h3>
              <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Price</h3>
              <h3 className="font-semibold text-center text-gray-600 text-xs uppercase w-1/5 text-center">Total</h3>
            </div>

            {/* // Hiển thị danh sách sản phẩm */}
            {order.cartProducts.map(cartProduct => 
            (
                <div key={cartProduct.id} className="flex items-center hover:bg-gray-100 -mx-8 px-6 py-5">
                  {/* Ảnh sản phẩm */}
                  <img src={cartProduct.product.linkImages[0]} alt={cartProduct.product.name} className="h-16 w-16 object-cover rounded" />
                  {/* Thông tin sản phẩm */}
                  <div className="flex flex-col justify-between ml-4 flex-grow">
                    <h3 className="text-sm font-medium">{cartProduct.product.name}</h3>
                    <span className="text-xs text-gray-500">ID: {cartProduct.product.id}</span>
                  </div>
                  {/* Số lượng */}
                  <div className="text-center text-gray-600 text-xs uppercase w-1/5 text-center">
                    <div className="flex items-center justify-center">
                        <button
                            className='bg-blue-500 text-white px-3 py-1 rounded mr-2'
                            onClick={() => handleChangeQuantity(cartProduct.product.id, -1)}
                            disabled={cartProduct.quantity <= 1}  
                          >
                            -
                        </button>
                      <span className="mx-2">{cartProduct.quantity}</span>
                      
                      <button
                          className='bg-blue-500 text-white px-3 py-1 rounded' 
                          onClick={() => handleChangeQuantity(cartProduct.product.id, 1)}
                        >
                          +  
                        </button>
                      
                    </div>
                  </div>
                  {/* Giá sản phẩm */}
                  <div className="text-center text-gray-600 text-xs uppercase w-1/5 text-center">
                    <h3 className="text-sm font-medium">${cartProduct.product.price}</h3>
                  </div>
                  {/* Tổng giá */}
                  <div className="text-center text-gray-600 text-xs uppercase w-1/5 text-center">
                    <span className="text-sm">${(cartProduct.product.price * cartProduct.quantity)}</span>
                  </div>

                  <div className="text-center text-gray-600 text-xs uppercase w-1/5 text-center">
                  <button
                    className="text-red-500 focus:outline-none"
                    onClick={() => {
                      handleAddProductToOrder()
                    }}
                  >
                    Add
                  </button>
                  <button
                    className="text-red-500 focus:outline-none"
                    onClick={() => handleRemoveFromCart(cartProduct.product.id)}
                  >
                    Remove
                  </button>
                </div>
                </div>
            
              )
            )}
                


            {/* Continue Shopping Link */}
            <a href="/" className="flex font-semibold text-indigo-600 text-sm mt-10">
              <svg className="fill-current mr-2 text-indigo-600 w-4" viewBox="0 0 448 512">
                {/* Icon */}
              </svg>
              Continue Shopping
            </a>
          </div>
          <div id="summary" className="w-1/4 px-8 py-10">
            <h1 className="font-semibold text-2xl border-b pb-8">Order Summary</h1>

            <div className="flex justify-between mt-10 mb-5">

            <span className="font-semibold text-sm uppercase">Total Price {order.totalAmount}</span>
              <span className="font-semibold text-sm">{order.totalPrice}</span>
            </div>

            <div className="py-10">
        <label htmlFor="promo" className="font-semibold inline-block mb-3 text-sm uppercase">Địa Chỉ</label>
        <input
          type="text"
          id="promo"
          className="p-2 text-sm w-full"
          style={{ width: '100%' }}
          value={address}
          onChange={(e) => setAddress(e.target.value)}
        />
      </div>

            <div className="border-t mt-8">

              <button className="bg-red-500 hover:bg-red-600 px-5 py-2 text-sm text-white uppercase" onClick={handleCheckout}>Apply</button>

            </div>
          </div>
          
        </div>
      </div>
    </div>
  );
};

export default ShoppingCart;
