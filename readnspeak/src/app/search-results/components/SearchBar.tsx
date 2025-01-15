'use client'

import React from 'react'

interface SearchBarProps {
  size?: 'large'
  shape?: 'rounded-f'
  color?: 'none'
  shadow?: 'full'
  onChange: React.ChangeEventHandler<HTMLInputElement> 
  value: string 
  onKeyDown?: React.KeyboardEventHandler<HTMLInputElement>  // onKeyDown prop 추가
}

const SearchBar: React.FC<SearchBarProps> = ({
  size = 'large',
  shape = 'rounded-f',
  color = 'none',
  shadow = 'full',
  value,
  onChange, 
  onKeyDown,  // onKeyDown 추가
}) => {

  // 크기
  const sizeClass = size === 'large' ? 'w-[80%]' : 'w-0'

  // 모양
  const shapeClass = shape === 'rounded-f' ? 'rounded-full' : 'rounded-none'

  // 색상
  const colorClass = color === 'none' ? 'border-white' : 'border-black'

  // 그림자
  const shadowClass = shadow === 'full' ? 'shadow-lg' : 'shadow-none'

  return (
    <div className={`items-center justify-center border py-3 pl-4 pr-10 font-bold text-gray-500 mr-4 bg-white ${sizeClass} ${shapeClass} ${colorClass} ${shadowClass}`}>
      <input
        type="text"
        value={value} 
        onChange={onChange}
        onKeyDown={onKeyDown}  // onKeyDown 이벤트를 input에 전달
        placeholder="찾고 싶은 도서 이름을 검색해주세요"
        className="w-full focus:outline-none"
      />
    </div>
  )
}

export default SearchBar
