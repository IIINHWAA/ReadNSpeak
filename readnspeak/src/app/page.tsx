'use client'

import React, { useState } from 'react'
import { useRouter } from 'next/navigation'
import { FaSearch } from 'react-icons/fa'
import Banner from '@/components/Banner'
import { BookList } from '@/components/BookList'
import CategoryBtn from '@/components/CategoryBtn'
import SearchBar from './search-results/components/SearchBar'

export default function Home() {
  const router = useRouter()
  const [searchQuery, setSearchQuery] = useState<string>('')

  const categories = [
    '잡지, 만화',
    '여행, 취미',
    '일본 서적',
    '외국어',
    '외국 서적',
    '유아 아동',
    '중고 학습',
    '경제, 경영',
  ]
  
  const handleSearch = () => {
    if (searchQuery.trim() !== '') {
      router.push(`/search-results?query=${searchQuery}`)
    }
  }
 
  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'Enter' && searchQuery.trim() !== '') {
      handleSearch()  
    }
  }

  return (
    <div>
      <Banner />
      <div className="flex flex-col items-center justify-center mt-16">
        <span className="font-extrabold text-xl">
          오늘도 다양한 도서를 만나보세요!
        </span>
      </div>

      <div className="mt-5 flex flex-row justify-center">
        <SearchBar
          size="large"
          shape="rounded-f"
          color="none"
          shadow="full"
          value={searchQuery}  
          onChange={(e) => setSearchQuery(e.target.value)} 
          onKeyDown={handleKeyDown}
        />
        <button type="button" className="ml-2" onClick={handleSearch}>
          <FaSearch className="h-5 w-5 text-gray-500" />
        </button>
      </div>

      <CategoryBtn categories={categories} />

      <BookList />
    </div>
  )
}
