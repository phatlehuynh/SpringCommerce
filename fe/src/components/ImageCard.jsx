// src/components/ProductCard.js
import React from "react";
import { Link } from "react-router-dom";

const ProductCard = ({ product }) => {
  return (
    
    <div key={product.id} className="group relative">
      <Link to={`/product/${product.id}`}>
      <div className="aspect-h-1 aspect-w-1 w-full overflow-hidden rounded-sm bg-gray-200 lg:aspect-none group-hover:opacity-75 lg:h-80">
          <img
              src={"https://via.placeholder.com/500"}//product.imageSrc
              alt={product.imageAlt}
              className="h-full w-full object-cover object-center lg:h-full lg:w-full"
          />
      </div>

      <div className="mt-4 flex justify-between">
          <div>
              <h3 className="text-sm text-gray-700">
                <a href={product.href}>
                    <span aria-hidden="true" className="absolute inset-0" />
                      {product.name}
                </a>
              </h3>
              <p className="mt-1 text-sm text-gray-500">{product.color}</p>
          </div>
          <p className="text-sm font-medium text-gray-900">${product.price}</p>
      </div>
      </Link>

    </div>
  );
};

export default ProductCard;
// https://via.placeholder.com/300