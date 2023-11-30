import React from 'react';
import { FaFacebook, FaTwitter, FaInstagram, FaYoutube } from "react-icons/fa6";

const Footer = () => {
  return (
    <footer className="footer bg-[#333333]">
      <div className="container mx-auto py-10 px-4 md:px-10 text-white">
        <div className="flex flex-col md:flex-row justify-between ">

          {/* Contact Us */}
          <div className="md:w-2/5">
            <h4 className="text-lg font-semibold">Contact Us</h4>
            <ul className="footer-link-list text-sm">
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Discipline - Politeness - Professionalism - Creativity - Devotion</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">19 Đ. Nguyễn Hữu Thọ, Tân Hưng, Quận 7, Thành phố Hồ Chí Minh</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">52100769@student.tdtu.edu.vn</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">(+84) 357549569</a>
              </li>
            </ul>
          </div>

          {/* Get Help */}
          <div className="md:w-1/5 mt-4 md:mt-0">
            <h4 className="text-lg font-semibold">Get Help</h4>
            <ul className="footer-link-list text-sm">
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Order Status</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Delivery</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Returns</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Payment Options</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Contact Us</a>
              </li>
            </ul>
          </div>

          {/* About */}
          <div className="md:w-1/5 mt-4 md:mt-0">
            <h4 className="text-lg font-semibold">About</h4>
            <ul className="footer-link-list text-sm">
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">News</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Careers</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Investors</a>
              </li>
              <li>
                <a href="#" className="text-gray-400 hover:text-white transition duration-300">Sustainability</a>
              </li>
            </ul>
          </div>

          {/* Social Icons */}
          <div className="md:w-1/5 mt-4 md:mt-0">
            <div className='flex flex-row space-x-4 '>
              <FaFacebook size={24} className="text-gray-400 hover:text-white transition duration-100 cursor-pointer" />
              <FaTwitter size={24} className="text-gray-400 hover:text-white transition duration-100 cursor-pointer" />
              <FaInstagram size={24} className="text-gray-400 hover:text-white transition duration-100 cursor-pointer" />
              <FaYoutube size={24} className="text-gray-400 hover:text-white transition duration-100 cursor-pointer" />
            </div>
          </div>
        </div>

        {/* Copyright */}
        <div className='flex flex-row pt-4 text-sm'>
          <p>&copy; 2023 Your Company Name. All rights reserved.</p>
        </div>
      </div>
    </footer>
  );
};

export default Footer;