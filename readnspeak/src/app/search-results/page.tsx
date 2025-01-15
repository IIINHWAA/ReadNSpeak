'use client'

import { FaSearch } from 'react-icons/fa'
import SearchBar from './components/SearchBar'
import { Suspense, useState } from 'react'
import SearchResult from './components/SearchResult'
import { useRouter } from 'next/navigation';

const Searchpages = () => {
  const [searchQuery, setSearchQuery] = useState<string>('')

  const router = useRouter() 

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
      <div className="flex-col w-full flex items-center justify-center">
        <span className="font-bold text-xl mt-10">오늘도 다양한 도서를 만나보세요!</span>

        <div className="mt-5 w-full flex flex-row justify-center">
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
      </div>

      <Suspense>
        <SearchResult />
      </Suspense>
    </div>
  )
}

export default Searchpages
