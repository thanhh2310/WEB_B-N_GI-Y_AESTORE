import { useState } from 'react';
import { Link } from 'react-router-dom';

const ProductDetail = () => {
  const [selectedSize, setSelectedSize] = useState(null);
  const [selectedColor, setSelectedColor] = useState(null);
  
  const sizes = [
    'EU 36', 'EU 36.5', 'EU 37.5', 'EU 38',
    'EU 38.5', 'EU 39', 'EU 40', 'EU 40.5',
    'EU 41'
  ];

  const colors = [
    { name: 'Black', code: '#000000' },
    { name: 'White', code: '#FFFFFF' },
    { name: 'Red', code: '#FF0000' },
    { name: 'Blue', code: '#0000FF' }
  ];

  const reviews = [
    {
      id: 1,
      user: 'John Doe',
      rating: 5,
      date: '2024-01-15',
      comment: 'Great shoes! Very comfortable and stylish.',
      images: ['/images/review1.jpg', '/images/review2.jpg']
    },
    {
      id: 2,
      user: 'Jane Smith',
      rating: 4,
      date: '2024-01-10',
      comment: 'Good quality but runs a bit small.',
      images: []
    }
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

          {/* Color Selection */}
          <div>
            <h2 className="font-medium mb-2">Select Color</h2>
            <div className="flex gap-2">
              {colors.map((color) => (
                <button
                  key={color.name}
                  className={`w-8 h-8 rounded-full border-2 ${
                    selectedColor === color.name 
                      ? 'border-black' 
                      : 'border-gray-300'
                  }`}
                  style={{ backgroundColor: color.code }}
                  onClick={() => setSelectedColor(color.name)}
                  title={color.name}
                />
              ))}
            </div>
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

          {/* Action Buttons */}
          <div className="space-y-4">
            <button className="w-full bg-black text-white py-4 rounded-full hover:bg-gray-800">
              Add to Bag
            </button>
            <button className="w-full bg-red-600 text-white py-4 rounded-full hover:bg-red-700">
              Buy Now
            </button>
            <button className="w-full border border-gray-300 py-4 rounded-full hover:border-black">
              Favorite
            </button>
          </div>
        </div>
      </div>

      {/* Product Details & Reviews */}
      <div className="mt-16">
        <div className="border-b border-gray-200">
          <nav className="flex gap-8">
            <button className="border-b-2 border-black py-4 font-medium">
              Product Details
            </button>
            <button className="py-4 text-gray-500 hover:text-black">
              Reviews ({reviews.length})
            </button>
          </nav>
        </div>

        {/* Product Details Section */}
        <div className="py-8">
          <h2 className="text-xl font-medium mb-4">Product Details</h2>
          <div className="space-y-4 text-gray-600">
            <p>
              The Nike V2K Run brings back the futuristic look of the early 2000s running shoes. 
              With a mix of synthetic materials and textile in the upper, plus Nike Air cushioning, 
              it delivers a bold look that's comfortable all day long.
            </p>
            <ul className="list-disc list-inside space-y-2">
              <li>Shown: White/Pure Platinum/Metallic Silver</li>
              <li>Style: DV3050-100</li>
              <li>Foam midsole</li>
              <li>Rubber outsole</li>
              <li>Textile and synthetic upper</li>
              <li>Imported</li>
            </ul>
          </div>
        </div>

        {/* Reviews Section */}
        <div className="py-8">
          <div className="flex justify-between items-center mb-6">
            <h2 className="text-xl font-medium">Customer Reviews</h2>
            <button className="px-6 py-2 border border-black rounded-full hover:bg-black hover:text-white">
              Write a Review
            </button>
          </div>

          <div className="space-y-8">
            {reviews.map((review) => (
              <div key={review.id} className="border-b border-gray-200 pb-8">
                <div className="flex justify-between mb-4">
                  <div>
                    <div className="flex items-center gap-2">
                      <div className="flex">
                        {[...Array(5)].map((_, i) => (
                          <svg
                            key={i}
                            className={`w-4 h-4 ${
                              i < review.rating ? 'text-yellow-400' : 'text-gray-300'
                            }`}
                            fill="currentColor"
                            viewBox="0 0 20 20"
                          >
                            <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z" />
                          </svg>
                        ))}
                      </div>
                      <span className="font-medium">{review.user}</span>
                    </div>
                    <p className="text-sm text-gray-500">{review.date}</p>
                  </div>
                </div>
                <p className="text-gray-600 mb-4">{review.comment}</p>
                {review.images.length > 0 && (
                  <div className="flex gap-4">
                    {review.images.map((image, index) => (
                      <img
                        key={index}
                        src={image}
                        alt={`Review ${index + 1}`}
                        className="w-20 h-20 object-cover rounded"
                      />
                    ))}
                  </div>
                )}
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default ProductDetail; 