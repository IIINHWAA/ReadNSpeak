import React from 'react'
import { FaSearch } from 'react-icons/fa'

interface SearchBarProps {
  size?: 'large'
  shape?: 'rounded-f'
  color?: 'none'
  shadow?: 'full'
}

const SearchBar: React.FC<SearchBarProps> = ({
  size = 'large',
  shape = 'rounded-f',
  color = 'none',
  shadow = 'full',
}) => {
  // 크기 설정: 화면 크기에 따라 60%로 고정
  const sizeClass = size === 'large' ? 'w-[60%]' : 'w-0'

  // 모양 설정
  const shapeClass = shape === 'rounded-f' ? 'rounded-full' : 'rounded-none'

  // 색상 설정
  const colorClass = color === 'none' ? 'border-white' : 'border-black'

  // 그림자 설정
  const shadowClass = shadow === 'full' ? 'shadow-lg' : 'shadow-none'

  return (
    <div className="w-full flex items-center justify-center">
      <input
        type="text"
        className={`border py-3 pl-4 pr-10 font-bold text-gray-500 ${sizeClass} ${shapeClass} ${colorClass} ${shadowClass} focus:outline-none`}
        placeholder="찾고 싶은 도서 이름을 검색해주세요"
      />
      <button type="button" className="absolute right-[28%]">
        <FaSearch className="h-5 w-5 text-gray-500" />
      </button>
    </div>
  )
}

export default SearchBar
