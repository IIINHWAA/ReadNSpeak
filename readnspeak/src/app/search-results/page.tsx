import SearchBar from '@/components/SearchBar'
import SearchBookList from '@/components/SearchBookList'

const Searchpages = () => {
  return (
    <div>
      <div className="flex-col w-full flex items-center justify-center">
        <span className="font-bold text-xl mt-10">
          오늘도 다양한 도서를 만나보세요!
        </span>
        <div className="mt-10 w-full">
          <SearchBar
            size="large"
            shape="rounded-f"
            color="none"
            shadow="full"
          />
        </div>
      </div>

      <SearchBookList />
    </div>
  )
}
export default Searchpages
