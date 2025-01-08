// SearchResults.tsx
import React from 'react'

interface CategoryResultsProps {
  category: string
}

const CategoryResults: React.FC<CategoryResultsProps> = ({ category }) => {
  return (
    <div className="mt-4">
      <h2 className="text-lg font-bold">{category} 검색 결과:</h2>
      {/* TODO: 실제 검색 결과 데이터를 여기에 표시 */}
      <p>해당 카테고리의 검색 결과를 여기에 표시합니다.</p>
    </div>
  )
}

export default CategoryResults
