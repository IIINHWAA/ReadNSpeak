
// SearchBar.tsx
import React from 'react';

interface SearchBarProps {
  size?: 'small' | 'medium' | 'large';
  shape?: 'rounded' | 'square';
  color?: 'blue' | 'red' | 'green';
}

const SearchBar: React.FC<SearchBarProps> = ({ size = 'medium', shape = 'rounded', color = 'blue' }) => {
  // 크기 설정
  const sizeClass = size === 'small' ? 'w-36' : size === 'medium' ? 'w-64' : 'w-96';

  // 모양 설정
  const shapeClass = shape === 'rounded' ? 'rounded-full' : 'rounded-none';

  // 색상 설정
  const colorClass =
    color === 'blue'
      ? 'border-blue-500'
      : color === 'red'
      ? 'border-red-500'
      : 'border-green-500';

  return (
    <div className="flex items-center space-x-2">
      <input
        type="text"
        className={`border p-2 ${sizeClass} ${shapeClass} ${colorClass} focus:outline-none focus:ring-2 focus:ring-blue-500`}
        placeholder="검색어를 입력하세요"
      />
      <button
        className={`bg-${color}-500 text-white p-2 rounded-lg hover:bg-${color}-700 transition-colors`}
      >
        검색
      </button>
    </div>
  );
};

export default SearchBar;

