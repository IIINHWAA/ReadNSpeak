'use client'

import { useSearchParams } from 'next/navigation'

const SearchResultsPage: React.FC = () => {
  const searchParams = useSearchParams()
  const category = searchParams.get('category')

  return (
    <div className="p-4">
      <h1 className="text-2xl font-bold">검색 결과</h1>
      <p>선택된 카테고리: {category}</p>
      {/* TODO: API를 호출하여 검색 결과를 표시 */}
      <div>
        <p>검색 결과가 여기에 표시됩니다.</p>
      </div>
    </div>
  )
}

export default SearchResultsPage
