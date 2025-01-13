import { FaStar } from 'react-icons/fa';
import { Book } from '../../../types'; 
import Image from 'next/image'

async function getBookData(id: string): Promise<Book | null> {
  const res = await fetch(`http://localhost:3000/data/books.json`);
  const books: Book[] = await res.json();
  return books.find(book => book.book_id.toString() === id) || null;
}

interface BookDetailPageProps {
  params: {
    id: string;
  };
}

export default async function BookDetailPage({ params }: BookDetailPageProps) {
  const book = await getBookData(params.id);

  if (!book) {
    return <div>책을 찾을 수 없습니다.</div>;
  }

  return (
    <div>
        <h2 className='font-bold mt-10 text-2xl'>📖 도서 상세 페이지</h2>
        <div className="flex flex-row mb-10 mt-7">
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

    </div>
  );
}
