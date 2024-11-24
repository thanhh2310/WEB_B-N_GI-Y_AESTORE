const Sidebar = () => {
  const filters = [
    {
      title: 'Shoes',
      count: 766
    },
    {
      title: 'Tops & T-Shirts',
      count: 192
    },
    {
      title: 'Shorts',
      count: 148
    },
    {
      title: 'Trousers & Tights',
      count: 123
    },
    {
      title: 'Accessories & Equipment',
      count: 98
    },
    {
      title: 'Jackets',
      count: 87
    },
    {
      title: 'Hoodies & Sweatshirts',
      count: 76
    },
    {
      title: 'Tracksuits',
      count: 54
    },
    {
      title: 'Compression & Baselayer',
      count: 43
    },
    {
      title: 'Socks',
      count: 32
    }
  ];

  const genderFilters = [
    {
      title: 'Men',
      count: 345
    },
    {
      title: 'Women',
      count: 289
    },
    {
      title: 'Unisex',
      count: 132
    }
  ];

  return (
    <aside className="w-64 pr-8">
      {/* Categories */}
      <div className="mb-8">
        <ul className="space-y-2">
          {filters.map((filter) => (
            <li key={filter.title}>
              <button className="w-full flex justify-between items-center py-1 hover:text-gray-700">
                <span>{filter.title}</span>
                <span className="text-gray-500">({filter.count})</span>
              </button>
            </li>
          ))}
        </ul>
      </div>

      {/* Gender Filter */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Gender</h3>
        <ul className="space-y-2">
          {genderFilters.map((filter) => (
            <li key={filter.title}>
              <label className="flex items-center space-x-2 cursor-pointer">
                <input type="checkbox" className="rounded border-gray-300 text-black focus:ring-black" />
                <span>{filter.title}</span>
                <span className="text-gray-500">({filter.count})</span>
              </label>
            </li>
          ))}
        </ul>
      </div>

      {/* Shop by Price */}
      <div className="mb-8">
        <h3 className="font-medium mb-4">Shop by Price</h3>
        <ul className="space-y-2">
          <li>
            <label className="flex items-center space-x-2 cursor-pointer">
              <input type="checkbox" className="rounded border-gray-300 text-black focus:ring-black" />
              <span>Under 1,000,000₫</span>
            </label>
          </li>
          <li>
            <label className="flex items-center space-x-2 cursor-pointer">
              <input type="checkbox" className="rounded border-gray-300 text-black focus:ring-black" />
              <span>1,000,000₫ - 2,000,000₫</span>
            </label>
          </li>
          <li>
            <label className="flex items-center space-x-2 cursor-pointer">
              <input type="checkbox" className="rounded border-gray-300 text-black focus:ring-black" />
              <span>2,000,000₫ - 4,000,000₫</span>
            </label>
          </li>
          <li>
            <label className="flex items-center space-x-2 cursor-pointer">
              <input type="checkbox" className="rounded border-gray-300 text-black focus:ring-black" />
              <span>Over 4,000,000₫</span>
            </label>
          </li>
        </ul>
      </div>
    </aside>
  );
};

export default Sidebar; 