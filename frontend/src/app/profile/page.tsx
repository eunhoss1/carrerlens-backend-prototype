import Card from '@/components/Card';
import Link from 'next/link';

export default function Page() {
  return <div className="space-y-4"><Card title="프로필 입력"><p>목표 국가/직무/기술 입력 후 저장</p></Card><Link className="text-blue-600" href="/jobs/recommendation">맞춤 채용 진단으로 이동</Link></div>;
}
