import Card from '@/components/Card';
import Link from 'next/link';

export default function Page() {
  return <div className="grid gap-4"><Card title="추천 공고 카드"><p>준비도: <span className="px-2 py-1 bg-amber-100 rounded">MEDIUM</span></p><ul className="list-disc ml-5"><li>Docker 보완</li><li>CI/CD 경험</li></ul></Card><Link href="/planner" className="text-blue-600">커리어 개발 플래너 생성</Link></div>;
}
