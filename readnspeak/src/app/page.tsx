import Banner from '@/components/Banner'
import SearchBar from '@/components/SearchBar'

export default function Home() {
  return (
    <div>
      <Banner />
      <div className="flex flex-col items-center justify-center mt-16">
        <span className="font-extrabold text-xl">
          오늘도 다양한 도서를 만나보세요!
        </span>
      </div>
      <div className="mt-5">
        <SearchBar size="large" shape="rounded-f" color="none" shadow="full" />
      </div>
    </div>
  )
}
