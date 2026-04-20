export default function Card({ title, children }: { title: string; children: React.ReactNode }) {
  return <section className="bg-white border rounded-xl p-4 shadow-sm"><h2 className="font-semibold mb-3">{title}</h2>{children}</section>;
}
