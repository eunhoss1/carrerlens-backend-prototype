import Card from '@/components/Card';
import Link from 'next/link';

export default function Page() {
  return <div className="grid md:grid-cols-5 gap-3">{['준비 중','제출 완료','서류 합격','면접','최종 결과'].map((s)=><Card key={s} title={s}><p className="text-sm">칸반 컬럼</p></Card>)}<Link href="/settlement" className="text-blue-600 md:col-span-5">초기 정착 체크리스트 보기</Link></div>;
}
