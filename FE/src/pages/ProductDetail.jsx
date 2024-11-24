import { useState } from 'react';

const ProductDetail = () => {
  const [selectedSize, setSelectedSize] = useState(null);
  
  const sizes = [
    'EU 36', 'EU 36.5', 'EU 37.5', 'EU 38',
    'EU 38.5', 'EU 39', 'EU 40', 'EU 40.5',
    'EU 41'
  ];

  return (
    <div className="container mx-auto px-4 py-8">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        {/* Product Images */}
        <div className="space-y-4">
          <img
            src="/path-to-product-image.jpg"
            alt="Nike V2K Run"
            className="w-full"
          />
          <div className="grid grid-cols-4 gap-2">
            {/* Thumbnail images */}
          </div>
        </div>

        {/* Product Info */}
        <div className="space-y-6">
          <div>
            <span className="text-red-600">Just In</span>
            <h1 className="text-2xl font-medium">Nike V2K Run</h1>
            <p className="text-nike-gray">Shoes</p>
            <p className="text-xl mt-2">3,519,000₫</p>
          </div>

          {/* Size Selection */}
          <div>
            <div className="flex justify-between items-center">
              <h2 className="font-medium">Select Size</h2>
              <button className="text-nike-gray hover:text-black">Size Guide</button>
            </div>
            <div className="grid grid-cols-3 gap-2 mt-4">
              {sizes.map((size) => (
                <button
                  key={size}
                  className={`border p-3 text-center hover:border-black ${
                    selectedSize === size ? 'border-black' : 'border-gray-300'
                  }`}
                  onClick={() => setSelectedSize(size)}
                >
                  {size}
                </button>
              ))}
            </div>
          </div>

          {/* Add to Bag & Favorite Buttons */}
          <div className="space-y-4">
            <button className="w-full bg-black text-white py-4 rounded-full hover:bg-gray-800">
              Add to Bag
            </button>
            <button className="w-full border border-gray-300 py-4 rounded-full hover:border-black">
              Favorite
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail; 