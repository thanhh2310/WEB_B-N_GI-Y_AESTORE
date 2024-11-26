const Sidebar = () => {
  const priceRanges = [
    {
      label: 'Dưới 1.000.000 đ',
      value: 'under-1m'
    },
    {
      label: '1.000.000 đ - 2.000.000 đ',
      value: '1m-2m'
    },
    {
      label: '2.000.000 đ - 4.000.000 đ',
      value: '2m-4m'
    },
    {
      label: '4.000.000 đ - 6.000.000 đ',
      value: '4m-6m'
    }
  ];

  const sizes = [
    { label: '35( 4 )', value: '35' },
    { label: '35.5( 20 )', value: '35.5' },
    { label: '36( 15 )', value: '36' },
    { label: '36.5( 16 )', value: '36.5' },
    { label: '37( 6 )', value: '37' }
  ];

  const brands = [
    { label: 'Adidas', count: 120 },
    { label: 'Converse', count: 98 },
    { label: 'Dior', count: 45 },
    { label: 'FILA', count: 67 }
  ];

  const colors = [
    { name: 'Black', code: '#000000', count: 56 },
    { name: 'White', code: '#FFFFFF', count: 48 },
    { name: 'Red', code: '#FF0000', count: 32 },
    { name: 'Blue', code: '#0000FF', count: 28 },
    { name: 'Green', code: '#00FF00', count: 24 }
  ];

  return (
    <aside className="w-64 pr-8">
      {/* Price Range */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Price range</h3>
        <p className="text-sm text-gray-500 mb-2">Khoảng giá</p>
        <ul className="space-y-2">
          {priceRanges.map((range) => (
            <li key={range.value}>
              <label className="flex items-center space-x-2 cursor-pointer">
                <input 
                  type="checkbox" 
                  className="rounded border-gray-300 text-black focus:ring-black" 
                />
                <span className="text-sm">{range.label}</span>
              </label>
            </li>
          ))}
        </ul>
        <button className="text-sm text-gray-500 mt-2 hover:text-black">
          + Show More
        </button>
      </div>

      {/* Size */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Size</h3>
        <p className="text-sm text-gray-500 mb-2">Kích thước</p>
        <ul className="space-y-2">
          {sizes.map((size) => (
            <li key={size.value}>
              <label className="flex items-center space-x-2 cursor-pointer">
                <input 
                  type="checkbox" 
                  className="rounded border-gray-300 text-black focus:ring-black" 
                />
                <span className="text-sm">{size.label}</span>
              </label>
            </li>
          ))}
        </ul>
        <button className="text-sm text-gray-500 mt-2 hover:text-black">
          + Show More
        </button>
      </div>

      {/* Brand */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Brand</h3>
        <p className="text-sm text-gray-500 mb-2">Thương hiệu</p>
        <ul className="space-y-2">
          {brands.map((brand) => (
            <li key={brand.label}>
              <label className="flex items-center space-x-2 cursor-pointer">
                <input 
                  type="checkbox" 
                  className="rounded border-gray-300 text-black focus:ring-black" 
                />
                <span className="text-sm">{brand.label}</span>
              </label>
            </li>
          ))}
        </ul>
      </div>

      {/* Colors */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Colors</h3>
        <p className="text-sm text-gray-500 mb-2">Màu sắc</p>
        <div className="grid grid-cols-4 gap-2">
          {colors.map((color) => (
            <div 
              key={color.name}
              className="flex flex-col items-center gap-1"
            >
              <button
                className="w-8 h-8 rounded-full border border-gray-300 hover:border-black"
                style={{ backgroundColor: color.code }}
                title={color.name}
              />
              <span className="text-xs text-gray-500">{color.count}</span>
            </div>
          ))}
        </div>
      </div>
    </aside>
  );
};

export default Sidebar; 