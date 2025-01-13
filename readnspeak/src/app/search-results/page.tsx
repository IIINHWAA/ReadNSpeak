'use client'

import { useRouter, useSearchParams } from 'next/navigation'
import { FaSearch, FaStar } from 'react-icons/fa'
import Image from 'next/image'
import SearchBar from './components/SearchBar'
import { useState } from 'react'
import books from '../../../public/data/books.json';

const Searchpages = () => {
  const SearchResultBookList = books;
  
  const searchParams = useSearchParams()
  const query = searchParams.get('query') || ''
  const router = useRouter()
  const [searchQuery, setSearchQuery] = useState<string>(query)


  const filteredBooks = SearchResultBookList.filter((book) =>
    book.title.toLowerCase().includes(searchQuery.toLowerCase())
  )

   const handleBookClick = (bookId: number) => {
    router.push(`/books/${bookId}`)
  }

  const handleSearch = () => {
    if (searchQuery.trim() !== '') {
      router.push(`/search-results?query=${searchQuery}`)
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
            onChange={(e) => setSearchQuery(e.target.value)}  // 입력값이 변경될 때마다 상태 업데이트
          />
          <button type="button" className="ml-2" onClick={handleSearch}>
            <FaSearch className="h-5 w-5 text-gray-500" />
          </button>
        </div>
      </div>

      <div className="flex flex-col mt-10 text-lg">
        <div className="flex flex-row mb-5 items-center">
          <span className="font-bold text-lg">검색 결과</span>
          <span className="ml-1 text-sm">({filteredBooks.length}개)</span>
        </div>

        <div className="border-b border-gray-400 w-full mb-10"></div>

        {filteredBooks.length > 0 ? (
          filteredBooks.map((book) => (
            <div key={book.book_id} className="flex flex-col" onClick={() => handleBookClick(book.book_id)}>
              <div className="flex flex-row mb-10">
                <Image
                  src={book.cover_image}
                  alt={book.title}
                  width={3000}
                  height={4000}
                  className="w-[20%] h-auto rounded-md shadow-xl"
                />

                <div className="flex flex-col ml-10 gap-2">
                  <h3 className="font-bold text-base">{book.title}</h3>
                  <p className="text-gray-500 text-base">{book.author}</p>
                  <div className="flex flex-row items-center">
                    <FaStar className="text-red-800 text-sm" />
                    <p className="text-red-800 ml-1 text-sm">{book.rating}</p>
                    <p className="text-gray-500 ml-1 text-sm">({book.comments})</p>
                  </div>
                  <p className="text-black text-xs font-bold">{book.description}</p>
                </div>
              </div>
              <div className="border-b border-gray-400 w-full mb-10"></div>
            </div>
          ))
        ) : (
          <div className="text-center text-gray-500">검색 결과가 없습니다.</div>
        )}
      </div>
    </div>
  )
}

export default Searchpages
