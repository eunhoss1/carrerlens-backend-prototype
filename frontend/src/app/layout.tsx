import './globals.css';
import Link from 'next/link';

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="ko">
      <body>
        <nav className="bg-white border-b p-3 flex gap-3 text-sm">
          <Link href="/login">Login</Link><Link href="/profile">Profile</Link><Link href="/jobs/recommendation">Recommendation</Link>
          <Link href="/planner">Planner</Link><Link href="/applications">Applications</Link><Link href="/settlement">Settlement</Link>
          <Link href="/verification">Verification</Link><Link href="/admin/verifications">Admin</Link><Link href="/recruiter/verified-candidates">Recruiter</Link>
        </nav>
        <main className="p-6">{children}</main>
      </body>
    </html>
  );
}
