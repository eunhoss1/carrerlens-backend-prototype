import Card from '@/components/Card';
import Link from 'next/link';

export default function Page() {
  return <div className="space-y-4"><Card title="3단계 로드맵"><ol className="list-decimal ml-5"><li>핵심 기술 보완</li><li>실전 경험 축적</li><li>서류 최적화</li></ol></Card><Link className="text-blue-600" href="/applications">기업 지원 시작</Link></div>;
}
