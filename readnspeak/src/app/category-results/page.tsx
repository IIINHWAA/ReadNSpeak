'use client'

import { Suspense } from 'react'
import CategoryList from './components/CategoryList'

const SearchResultsPage: React.FC = () => {
  return (
    <div>
      <Suspense>
        <CategoryList />
      </Suspense>
    </div>
  )
}

export default SearchResultsPage
