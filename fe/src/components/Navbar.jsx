import React, { useState, useEffect } from 'react';
import { AiOutlineMenu, AiOutlineSearch, AiOutlineClose, AiFillTag, AiOutlineUser } from 'react-icons/ai';
import { MdOutlineWorkHistory } from "react-icons/md";
import { BsFillCartFill, BsFillSaveFill } from 'react-icons/bs';
import { TbTruckDelivery } from 'react-icons/tb';
import { FaUserFriends, FaWallet } from 'react-icons/fa';
import { MdFavorite, MdHelp } from 'react-icons/md';
import { GrUserAdmin } from "react-icons/gr";
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';

const Navbar = () => {
  const [nav, setNav] = useState(false);
  const [userData, setUserData] = useState(null);
  const navigate = useNavigate();
  const [searchString, setSearchString] = useState('');

  const handleSearch = () => {
    // Kiểm tra xem input có giá trị không
    if (searchString.trim() !== '') {
      // Chuyển hướng tới đường dẫn `/Search/:string`
      navigate(`/Search/${searchString}`);
    } else {
      // Nếu không có giá trị, chuyển hướng đến trang mặc định
      alert('vui lòng nhập tên sản phẩm muốn tìm');
      navigate('/');

    }
  };
  useEffect(() => {
    const storedUserData = JSON.parse(localStorage.getItem('user'));
    setUserData(storedUserData);
  }, []);

  useEffect(() => {
    const handleStorageChange = () => {
      const storedUserData = JSON.parse(localStorage.getItem('user'));
      setUserData(storedUserData);
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, [userData]);

  const handleLogout = () => {
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    setUserData(null);
  };

  return (
    <div className='max-w-[1640px] mx-auto flex justify-between items-center p-4'>
      {/* Left side */}
      <div className='flex items-center'>
        <div onClick={() => setNav(!nav)} className='cursor-pointer'>
          <AiOutlineMenu size={30} />
        </div>
        <Link to="/">
          <h1 className='text-2xl sm:text-3xl lg:text-4xl px-2'>
            Konan <span className='font-bold'>Tune</span>
          </h1>
        </Link>
        <div className='hidden lg:flex items-center bg-gray-200 rounded-full p-1 text-[14px]'>
          {userData ? (
            <button
              onClick={handleLogout}
              className='bg-red-500 text-white rounded-full p-2 mr-2'
            >
              Log Out
            </button>
          ) : (
            <>
              <Link to="/login" className='bg-black text-white rounded-full p-2 mr-2'>
                Login
              </Link>
              <Link to="/link" className='p-2'>
                Sign Up
              </Link>
            </>
          )}
        </div>
      </div>
      <div className='bg-gray-200 rounded-full flex items-center px-2 w-[200px] sm:w-[400px] lg:w-[500px]'>
      <button className="border-0" onClick={handleSearch}>
        <AiOutlineSearch size={25} />
      </button>
      <input
        id='searchInput'
        className='bg-transparent p-2 w-full focus:outline-none'
        type='text'
        placeholder='Search Product'
        value={searchString}
        onChange={(e) => setSearchString(e.target.value)}
      />
    </div>

{/* Cart button */}
{userData && userData.role === 'USER' && (
  <div className="flex space-x-4">
  <Link to="/Cart">
    <button className="bg-black text-white hidden md:flex items-center py-2 rounded-full">
      <BsFillCartFill size={20} className="mr-2" />
      Cart
    </button>
  </Link>
  <Link to="/bill">
  <button className="bg-black text-white hidden md:flex items-center py-2 rounded-full">
    <MdOutlineWorkHistory size={20} className="mr-2" />
    Bill
  </button>
  </Link>
</div>
)}
{userData && userData.role === 'ADMIN' && (
  <Link to="/Product/Admin">
    <button className='bg-red text-white hidden md:flex items-center py-2 rounded-full'>
      <GrUserAdmin size={20} className='mr-2' />
    </button>
  </Link>
)}
      {userData? (
      <>
            <button
              onClick={handleLogout}
              className='bg-black text-white md:hidden px-3 py-1 rounded-full'
            >
              Log Out
            </button>
      </>)
      :
      (<>
        <Link to="/login" className='bg-black text-white md:hidden px-3 py-1 rounded-full'>
        <AiOutlineUser size={20} className='mr-2' /> Login
        </Link>
      </>)}
      {/* Mobile Menu */}
      {nav ? <div className='bg-black/80 fixed w-full h-screen z-10 top-0 left-0'></div> : ''}
      {/* Side drawer menu */}
      <div className={nav ? 'fixed top-0 left-0 w-[300px] h-screen bg-white z-10 duration-300' : 'fixed top-0 left-[-100%] w-[300px] h-screen bg-white z-10 duration-300'}>
        <AiOutlineClose
          onClick={() => setNav(!nav)}
          size={30}
          className='absolute right-4 top-4 cursor-pointer'
        />
        <h2 className='text-2xl p-4'>
          Best <span className='font-bold'>Eats</span>
        </h2>
        <nav>
        {userData && userData.role === 'USER' && (
                    <ul className='flex flex-col p-4 text-gray-800'>
                    <li className='text-xl py-4 flex'><TbTruckDelivery size={25} className='mr-4' /> Orders</li>
                    <li className='text-xl py-4 flex'><MdFavorite size={25} className='mr-4' /> Bill</li>
                  </ul>
        )}
                {userData && userData.role === 'ADMIN' && (
                    <ul className='flex flex-col p-4 text-gray-800'>
                    <li className='text-xl py-4 flex'><TbTruckDelivery size={25} className='mr-4' /> Admin</li>

                  </ul>
        )}
        </nav>
      </div>
    </div>
  );
};

export default Navbar;
