import { useState } from "react";

const C = {
  bg:"#080B12", surf:"#0F1319", surf2:"#161B26", surf3:"#1D2333",
  border:"#252D40", green:"#22C97A", purple:"#8875F5", blue:"#4B9EF8",
  amber:"#F5A623", red:"#F56565", pink:"#F472B6",
  text:"#DCE1EE", sub:"#8490A8", muted:"#4A5268",
};

// ─── PRIMITIVES ───────────────────────────────────────────────────────────────
const s = (obj) => obj;

function Lbl({ c, children }) {
  return <div style={{ fontSize:10, color:c||C.sub, fontWeight:600, marginBottom:4, textTransform:"uppercase", letterSpacing:"0.06em" }}>{children}</div>;
}
function Field({ label, children }) {
  return <div style={{ marginBottom:10 }}>{label && <Lbl>{label}</Lbl>}{children}</div>;
}
function Input({ id, type, placeholder, value, onChange, style }) {
  return (
    <input id={id} type={type||"text"} placeholder={placeholder} value={value} onChange={onChange}
      style={{ width:"100%", background:C.surf3, border:`1px solid ${C.border}`, borderRadius:6,
        color:C.text, padding:"7px 10px", fontSize:11, outline:"none", fontFamily:"inherit", ...style }}/>
  );
}
function Select({ value, onChange, children }) {
  return (
    <select value={value} onChange={onChange}
      style={{ width:"100%", background:C.surf3, border:`1px solid ${C.border}`, borderRadius:6,
        color:C.text, padding:"7px 10px", fontSize:11, outline:"none", fontFamily:"inherit", cursor:"pointer" }}>
      {children}
    </select>
  );
}
function Btn({ children, onClick, color, outline, full, small, danger }) {
  const bg = danger ? C.red+"22" : outline ? "transparent" : color||C.green;
  const col = danger ? C.red : outline ? (color||C.sub) : "#000";
  const border = outline||danger ? `1px solid ${danger?C.red:color||C.border}` : "none";
  return (
    <button onClick={onClick} style={{
      background:bg, color:col, border, borderRadius:6,
      padding: small ? "4px 10px" : "7px 14px",
      fontSize: small ? 10 : 11, fontWeight:700, cursor:"pointer",
      width: full ? "100%" : "auto",
    }}>{children}</button>
  );
}
function Badge({ children, color }) {
  return <span style={{ background:color+"22", color, borderRadius:4, padding:"2px 7px", fontSize:10, fontWeight:600 }}>{children}</span>;
}
function Card({ children, pad, style }) {
  return <div style={{ background:C.surf, border:`1px solid ${C.border}`, borderRadius:8, padding:pad||12, ...style }}>{children}</div>;
}
function Row({ children, style }) {
  return <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center", ...style }}>{children}</div>;
}
function Grid({ cols, gap, children }) {
  return <div style={{ display:"grid", gridTemplateColumns:cols, gap:gap||10 }}>{children}</div>;
}
function PageTitle({ title, action }) {
  return (
    <Row style={{ marginBottom:14 }}>
      <div style={{ fontSize:14, fontWeight:700, color:C.text }}>{title}</div>
      {action}
    </Row>
  );
}
function Bar({ pct, color, h }) {
  const c = color || (pct>100 ? C.red : pct>75 ? C.amber : C.green);
  return (
    <div style={{ background:C.surf3, borderRadius:4, height:h||6, overflow:"hidden" }}>
      <div style={{ width:`${Math.min(pct,100)}%`, height:"100%", background:c, borderRadius:4 }}/>
    </div>
  );
}
function Toggle({ on, onChange }) {
  return (
    <div onClick={()=>onChange(!on)} style={{ width:36, height:20, borderRadius:10,
      background:on?C.green:C.muted+"55", position:"relative", cursor:"pointer", flexShrink:0 }}>
      <div style={{ position:"absolute", top:3, left:on?17:3, width:14, height:14,
        borderRadius:"50%", background:"#fff", transition:"left .2s" }}/>
    </div>
  );
}
function FormPanel({ title, onClose, children }) {
  return (
    <Card style={{ marginBottom:12 }}>
      <Row style={{ marginBottom:12 }}>
        <div style={{ fontSize:12, fontWeight:700, color:C.text }}>{title}</div>
        <button onClick={onClose} style={{ background:"none", border:"none", color:C.muted, cursor:"pointer", fontSize:18, lineHeight:1 }}>×</button>
      </Row>
      {children}
    </Card>
  );
}
function TypeToggle({ value, onChange }) {
  return (
    <div style={{ display:"flex", gap:6 }}>
      {["Expense","Deposit"].map(t=>(
        <button key={t} onClick={()=>onChange(t)} style={{
          flex:1, padding:"6px 0", fontSize:11, border:"none", borderRadius:6, cursor:"pointer",
          background: value===t ? (t==="Expense"?C.red:C.green)+"33" : C.surf3,
          color: value===t ? (t==="Expense"?C.red:C.green) : C.muted,
          fontWeight: value===t ? 700 : 400,
        }}>{t}</button>
      ))}
    </div>
  );
}
function Divider() { return <div style={{ borderTop:`1px solid ${C.border}`, margin:"14px 0" }}/>; }

// ─── DONUT CHART ──────────────────────────────────────────────────────────────
function Donut({ segs, size, sw, label, sub }) {
  const s=size||100, stroke=sw||15, cx=s/2, cy=s/2, r=cx-stroke/2-2;
  let cum=0;
  const arcs=segs.map(seg=>{
    const a1=cum/100*2*Math.PI-Math.PI/2; cum+=seg.pct;
    const a2=cum/100*2*Math.PI-Math.PI/2;
    const x1=cx+r*Math.cos(a1),y1=cy+r*Math.sin(a1),x2=cx+r*Math.cos(a2),y2=cy+r*Math.sin(a2);
    return {...seg, d:`M${x1.toFixed(1)} ${y1.toFixed(1)} A${r} ${r} 0 ${seg.pct>50?1:0} 1 ${x2.toFixed(1)} ${y2.toFixed(1)}`};
  });
  return (
    <svg width={s} height={s} viewBox={`0 0 ${s} ${s}`} style={{flexShrink:0}}>
      {arcs.map((a,i)=><path key={i} d={a.d} fill="none" stroke={a.color} strokeWidth={stroke}/>)}
      <circle cx={cx} cy={cy} r={r-stroke/2-1} fill={C.bg}/>
      {label&&<text x={cx} y={cy-3} textAnchor="middle" fill={C.text} fontSize="10" fontWeight="bold">{label}</text>}
      {sub&&<text x={cx} y={cy+9} textAnchor="middle" fill={C.muted} fontSize="8">{sub}</text>}
    </svg>
  );
}
function Legend({ segs }) {
  return (
    <div style={{ flex:1 }}>
      {segs.map(s=>(
        <div key={s.label} style={{ display:"flex", alignItems:"center", gap:5, marginBottom:5 }}>
          <div style={{ width:7, height:7, borderRadius:2, background:s.color, flexShrink:0 }}/>
          <div style={{ fontSize:9, color:C.sub, flex:1 }}>{s.label}</div>
          <div style={{ fontSize:9, color:C.text, fontFamily:"monospace" }}>{s.pct}%</div>
        </div>
      ))}
    </div>
  );
}
function ChartCard({ title, segs }) {
  const top = segs.reduce((a,b)=>b.pct>a.pct?b:a, segs[0]);
  return (
    <Card>
      <Lbl>{title}</Lbl>
      <div style={{ display:"flex", alignItems:"center", gap:8, marginTop:6 }}>
        <Donut segs={segs} label={top.pct+"%"} sub={top.label}/>
        <Legend segs={segs}/>
      </div>
    </Card>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  AUTH — LOGIN
// ═══════════════════════════════════════════════════════════════════════════════
function LoginScreen({ onLogin, onGoSignUp }) {
  const [err, setErr] = useState(false);
  function attempt() {
    const u = document.getElementById("li-u")?.value;
    const p = document.getElementById("li-p")?.value;
    if (!u || !p) { setErr(true); return; }
    setErr(false); onLogin();
  }
  return (
    <div style={{ minHeight:"100vh", background:C.bg, display:"flex", alignItems:"center", justifyContent:"center", padding:20 }}>
      <div style={{ width:"100%", maxWidth:340 }}>
        <div style={{ textAlign:"center", marginBottom:28 }}>
          <div style={{ fontSize:28, fontWeight:900, color:C.green, letterSpacing:"-0.04em" }}>$penderman</div>
          <div style={{ fontSize:11, color:C.muted, marginTop:4 }}>Sign in to your account</div>
        </div>
        <Card pad={24}>
          <div style={{ fontSize:14, fontWeight:700, color:C.text, marginBottom:16 }}>Welcome back</div>
          <Field label="Username"><Input id="li-u" placeholder="Your username" onKeyDown={e=>e.key==="Enter"&&attempt()}/></Field>
          <Field label="Password"><Input id="li-p" type="password" placeholder="Your password" onKeyDown={e=>e.key==="Enter"&&attempt()}/></Field>
          {err && (
            <div style={{ fontSize:10, color:C.red, background:C.red+"14", border:`1px solid ${C.red}33`, borderRadius:6, padding:"6px 10px", marginBottom:10 }}>
              ⚠ Please fill in both fields.
            </div>
          )}
          <button onClick={attempt} style={{ width:"100%", background:C.green, color:"#000", border:"none", borderRadius:7, padding:"9px 0", fontSize:12, fontWeight:800, cursor:"pointer", marginTop:4 }}>
            Sign In
          </button>
          <div style={{ textAlign:"center", marginTop:14, fontSize:11, color:C.muted }}>
            No account?{" "}
            <span onClick={onGoSignUp} style={{ color:C.green, cursor:"pointer", fontWeight:600 }}>Create one</span>
          </div>
        </Card>
      </div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  AUTH — SIGN UP
// ═══════════════════════════════════════════════════════════════════════════════
function SignUpScreen({ onSignUp, onGoLogin }) {
  const [err, setErr] = useState("");
  const [ok, setOk] = useState(false);
  function attempt() {
    const fn=document.getElementById("su-fn")?.value?.trim();
    const ln=document.getElementById("su-ln")?.value?.trim();
    const u=document.getElementById("su-u")?.value?.trim();
    const p=document.getElementById("su-p")?.value;
    if (!fn||!ln||!u||!p) { setErr("All fields are required."); return; }
    if (p.length<6) { setErr("Password must be at least 6 characters."); return; }
    setErr(""); setOk(true);
    setTimeout(()=>onSignUp(), 900);
  }
  return (
    <div style={{ minHeight:"100vh", background:C.bg, display:"flex", alignItems:"center", justifyContent:"center", padding:20 }}>
      <div style={{ width:"100%", maxWidth:360 }}>
        <div style={{ textAlign:"center", marginBottom:28 }}>
          <div style={{ fontSize:28, fontWeight:900, color:C.green, letterSpacing:"-0.04em" }}>$penderman</div>
          <div style={{ fontSize:11, color:C.muted, marginTop:4 }}>Create your account</div>
        </div>
        <Card pad={24}>
          <div style={{ fontSize:14, fontWeight:700, color:C.text, marginBottom:16 }}>Create account</div>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="First Name"><Input id="su-fn" placeholder="Ahmed"/></Field>
            <Field label="Last Name"><Input id="su-ln" placeholder="Hassan"/></Field>
          </Grid>
          <Field label="Username"><Input id="su-u" placeholder="Choose a username"/></Field>
          <Field label="Password"><Input id="su-p" type="password" placeholder="Min 6 characters" onKeyDown={e=>e.key==="Enter"&&attempt()}/></Field>
          {err && <div style={{ fontSize:10, color:C.red, background:C.red+"14", border:`1px solid ${C.red}33`, borderRadius:6, padding:"6px 10px", marginBottom:10 }}>⚠ {err}</div>}
          {ok  && <div style={{ fontSize:10, color:C.green, background:C.green+"14", border:`1px solid ${C.green}33`, borderRadius:6, padding:"6px 10px", marginBottom:10 }}>✓ Account created! Signing you in…</div>}
          <button onClick={attempt} style={{ width:"100%", background:C.green, color:"#000", border:"none", borderRadius:7, padding:"9px 0", fontSize:12, fontWeight:800, cursor:"pointer", marginTop:4 }}>
            Create Account
          </button>
          <div style={{ textAlign:"center", marginTop:14, fontSize:11, color:C.muted }}>
            Already have an account?{" "}
            <span onClick={onGoLogin} style={{ color:C.green, cursor:"pointer", fontWeight:600 }}>Sign in</span>
          </div>
        </Card>
      </div>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  DASHBOARD
// ═══════════════════════════════════════════════════════════════════════════════
const expSegs=[{label:"Food",pct:35,color:C.amber},{label:"Transport",pct:20,color:C.purple},{label:"Utilities",pct:15,color:C.pink},{label:"Entertainment",pct:18,color:C.blue},{label:"Other",pct:12,color:C.muted}];
const depSegs=[{label:"Salary",pct:72,color:C.green},{label:"Freelance",pct:18,color:C.blue},{label:"Other",pct:10,color:C.muted}];
const walSegs=[{label:"Business",pct:78,color:C.purple},{label:"Main Bank",pct:21,color:C.green},{label:"Pocket Cash",pct:1,color:C.blue}];

function DashboardScreen() {
  const [hasCycle] = useState(true);
  return (
    <div>
      <PageTitle title="Dashboard"/>
      {/* Total balance */}
      <div style={{ background:`${C.surf}`, border:`1px solid ${C.green}33`, borderRadius:10, padding:"14px 16px", marginBottom:12 }}>
        <Lbl c={C.sub}>Total Balance</Lbl>
        <div style={{ fontSize:26, fontWeight:900, color:C.green, fontFamily:"monospace", letterSpacing:"-0.02em", marginBottom:10 }}>EGP 58,450.00</div>
        <Grid cols="repeat(3,1fr)" gap={8}>
          {[{n:"Main Bank",b:"12,400",c:C.green},{n:"Pocket Cash",b:"850",c:C.blue},{n:"Business",b:"45,200",c:C.purple}].map(w=>(
            <div key={w.n} style={{ background:C.surf2, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 10px" }}>
              <div style={{ fontSize:9, color:C.muted, marginBottom:2 }}>{w.n}</div>
              <div style={{ fontSize:12, fontWeight:700, color:w.c, fontFamily:"monospace" }}>EGP {w.b}</div>
            </div>
          ))}
        </Grid>
      </div>
      {/* Cycle */}
      {hasCycle ? (
        <Card style={{ marginBottom:12 }}>
          <Row style={{ marginBottom:10 }}>
            <div>
              <Lbl>Active Cycle</Lbl>
              <div style={{ fontSize:13, fontWeight:700, color:C.text }}>April 2025</div>
              <div style={{ fontSize:9, color:C.muted }}>Apr 1 → Apr 30</div>
            </div>
            <Badge color={C.green}>Active</Badge>
          </Row>
          <Grid cols="repeat(3,1fr)" gap={8} style={{ marginBottom:8 }}>
            {[{l:"Budget",v:"5,000",c:C.text},{l:"Spent",v:"3,200",c:C.amber},{l:"Remaining",v:"1,800",c:C.green}].map(s=>(
              <div key={s.l} style={{ textAlign:"center" }}>
                <div style={{ fontSize:9, color:C.muted, marginBottom:2 }}>{s.l}</div>
                <div style={{ fontSize:13, fontWeight:700, color:s.c, fontFamily:"monospace" }}>EGP {s.v}</div>
              </div>
            ))}
          </Grid>
          <Bar pct={64}/>
          <div style={{ fontSize:9, color:C.muted, marginTop:4, textAlign:"right" }}>64% of budget used</div>
        </Card>
      ) : (
        <Card style={{ marginBottom:12, textAlign:"center", padding:"16px 12px" }}>
          <div style={{ fontSize:16, color:C.muted, marginBottom:4 }}>◎</div>
          <div style={{ fontSize:12, color:C.sub, fontWeight:600 }}>No Active Cycle</div>
          <div style={{ fontSize:10, color:C.muted, marginTop:2 }}>Create a cycle to start tracking your budget</div>
        </Card>
      )}
      {/* Three charts */}
      <Grid cols="1fr 1fr 1fr" gap={10}>
        <ChartCard title="Expenses by Category" segs={expSegs}/>
        <ChartCard title="Deposits by Category" segs={depSegs}/>
        <ChartCard title="Balance by Wallet"    segs={walSegs}/>
      </Grid>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  TRANSACTIONS
// ═══════════════════════════════════════════════════════════════════════════════
function TransactionsScreen() {
  const [showForm, setShowForm] = useState(false);
  const [target, setTarget]   = useState("wallet");
  const [txType, setTxType]   = useState("Expense");
  const rows = [
    {n:"Lunch at Koshary",  dest:"Pocket Cash",  isGoal:false, cat:"Food",       t:"Expense", a:-85,   d:"2025-04-15"},
    {n:"April Salary",      dest:"Main Bank",     isGoal:false, cat:"Salary",     t:"Deposit", a:8500,  d:"2025-04-01"},
    {n:"Uber ride",         dest:"Pocket Cash",  isGoal:false, cat:"Transport",  t:"Expense", a:-45,   d:"2025-04-14"},
    {n:"Laptop fund",       dest:"New Laptop",   isGoal:true,  cat:"Savings",    t:"Expense", a:-500,  d:"2025-04-10"},
    {n:"Electricity bill",  dest:"Main Bank",     isGoal:false, cat:"Utilities",  t:"Expense", a:-320,  d:"2025-04-05"},
  ];
  return (
    <div>
      <PageTitle title="Transactions" action={<Btn small onClick={()=>setShowForm(!showForm)}>+ Add</Btn>}/>
      {showForm && (
        <FormPanel title="New Transaction" onClose={()=>setShowForm(false)}>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="Amount (EGP)"><Input type="number" placeholder="0.00"/></Field>
            <Field label="Type"><TypeToggle value={txType} onChange={setTxType}/></Field>
          </Grid>
          <Field label="Target">
            <div style={{ display:"flex", gap:6, marginBottom:6 }}>
              {[{id:"wallet",label:"◈ Wallet"},{id:"goal",label:"◇ Saving Goal"}].map(o=>(
                <button key={o.id} onClick={()=>setTarget(o.id)} style={{
                  flex:1, padding:"5px 0", fontSize:10, cursor:"pointer", borderRadius:6,
                  border:`1px solid ${target===o.id?C.green:C.border}`,
                  background: target===o.id ? C.green+"18" : C.surf3,
                  color: target===o.id ? C.green : C.muted,
                }}>{o.label}</button>
              ))}
            </div>
            {target==="wallet"
              ? <Select><option>Main Bank Account</option><option>Pocket Cash</option><option>Business Account</option></Select>
              : <Select><option>New Laptop</option><option>Summer Vacation</option></Select>}
          </Field>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="Category">
              <Select><option>Food</option><option>Transport</option><option>Salary</option><option>Utilities</option><option>Entertainment</option><option>Freelance</option></Select>
            </Field>
            <Field label="Date"><Input type="date" defaultValue="2025-04-25"/></Field>
          </Grid>
          <Field label="Note (optional)">
            <textarea placeholder="Add a note…" style={{ width:"100%", height:52, background:C.surf3, border:`1px solid ${C.border}`, borderRadius:6, color:C.text, padding:"7px 10px", fontSize:11, outline:"none", fontFamily:"inherit", resize:"none" }}/>
          </Field>
          <div style={{ display:"flex", gap:8, justifyContent:"flex-end" }}>
            <Btn outline onClick={()=>setShowForm(false)}>Cancel</Btn>
            <Btn>Save Transaction</Btn>
          </div>
        </FormPanel>
      )}
      <Card pad={0}>
        <table style={{ width:"100%", borderCollapse:"collapse", fontSize:11 }}>
          <thead>
            <tr style={{ background:C.surf2 }}>
              {["Description","Target","Category","Type","Amount","Date"].map(h=>(
                <th key={h} style={{ padding:"8px 12px", textAlign:"left", color:C.muted, fontWeight:600, fontSize:10 }}>{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {rows.map((r,i)=>(
              <tr key={i} style={{ borderTop:`1px solid ${C.border}22` }}>
                <td style={{ padding:"7px 12px", color:C.text }}>{r.n}</td>
                <td style={{ padding:"7px 12px", color:r.isGoal?C.pink:C.sub }}>{r.isGoal?"◇ ":"◈ "}{r.dest}</td>
                <td style={{ padding:"7px 12px", color:C.sub }}>{r.cat}</td>
                <td style={{ padding:"7px 12px" }}><Badge color={r.t==="Deposit"?C.green:C.red}>{r.t}</Badge></td>
                <td style={{ padding:"7px 12px", color:r.a>0?C.green:C.red, fontFamily:"monospace" }}>{r.a>0?"+":""}{r.a.toLocaleString()} EGP</td>
                <td style={{ padding:"7px 12px", color:C.muted }}>{r.d}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </Card>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  WALLETS
// ═══════════════════════════════════════════════════════════════════════════════
function WalletsScreen() {
  const [showForm, setShowForm] = useState(false);
  const wallets = [
    {n:"Main Bank Account", b:"12,400.00", tx:24, c:C.green},
    {n:"Pocket Cash",       b:"850.00",    tx:8,  c:C.blue},
    {n:"Business Account",  b:"45,200.00", tx:12, c:C.purple},
  ];
  return (
    <div>
      <PageTitle title="Wallets" action={<Btn small onClick={()=>setShowForm(!showForm)}>+ New Wallet</Btn>}/>
      {showForm && (
        <FormPanel title="New Wallet" onClose={()=>setShowForm(false)}>
          <Field label="Wallet Name"><Input placeholder='e.g. "Main Bank Account"'/></Field>
          <div style={{ display:"flex", gap:8, justifyContent:"flex-end" }}>
            <Btn outline onClick={()=>setShowForm(false)}>Cancel</Btn>
            <Btn>Create Wallet</Btn>
          </div>
        </FormPanel>
      )}
      <Grid cols="1fr 1fr" gap={12}>
        {wallets.map(w=>(
          <Card key={w.n} pad={16}>
            <Row style={{ marginBottom:12 }}>
              <div style={{ width:36, height:36, borderRadius:8, background:w.c+"22", display:"flex", alignItems:"center", justifyContent:"center", fontSize:16, color:w.c }}>◈</div>
              <Btn small outline danger>Delete</Btn>
            </Row>
            <div style={{ fontSize:10, color:C.muted, marginBottom:2 }}>{w.n}</div>
            <div style={{ fontSize:20, fontWeight:800, color:w.c, fontFamily:"monospace", marginBottom:8 }}>EGP {w.b}</div>
            <div style={{ fontSize:9, color:C.muted, paddingTop:8, borderTop:`1px solid ${C.border}` }}>{w.tx} transactions</div>
          </Card>
        ))}
      </Grid>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  CATEGORIES
// ═══════════════════════════════════════════════════════════════════════════════
function CategoriesScreen() {
  const [showForm, setShowForm] = useState(false);
  const [catType, setCatType] = useState("Expense");
  const cats = [
    {n:"Food",          c:"#F59E0B", t:"Expense", k:18},
    {n:"Transport",     c:C.purple,  t:"Expense", k:7},
    {n:"Salary",        c:C.green,   t:"Deposit", k:2},
    {n:"Utilities",     c:C.pink,    t:"Expense", k:4},
    {n:"Entertainment", c:C.blue,    t:"Expense", k:6},
    {n:"Freelance",     c:"#F59E0B", t:"Deposit", k:3},
  ];
  return (
    <div>
      <PageTitle title="Categories" action={<Btn small onClick={()=>setShowForm(!showForm)}>+ New</Btn>}/>
      {showForm && (
        <FormPanel title="New Category" onClose={()=>setShowForm(false)}>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="Name"><Input placeholder='e.g. "Food"'/></Field>
            <Field label="Hex Color"><Input placeholder="#F59E0B"/></Field>
          </Grid>
          <Field label="Type"><TypeToggle value={catType} onChange={setCatType}/></Field>
          <div style={{ display:"flex", gap:8, justifyContent:"flex-end" }}>
            <Btn outline onClick={()=>setShowForm(false)}>Cancel</Btn>
            <Btn>Create Category</Btn>
          </div>
        </FormPanel>
      )}
      <Grid cols="repeat(3,1fr)" gap={10}>
        {cats.map(cat=>(
          <Card key={cat.n}>
            <div style={{ display:"flex", alignItems:"center", gap:10 }}>
              <div style={{ width:32, height:32, borderRadius:8, background:cat.c, flexShrink:0 }}/>
              <div style={{ flex:1, minWidth:0 }}>
                <div style={{ fontSize:12, color:C.text, fontWeight:600 }}>{cat.n}</div>
                <div style={{ fontSize:9, marginTop:2, color:cat.t==="Deposit"?C.green:C.red }}>{cat.t} · {cat.k} txns</div>
              </div>
              <button style={{ background:"none", border:"none", color:C.muted, cursor:"pointer", fontSize:14, flexShrink:0 }}>×</button>
            </div>
          </Card>
        ))}
      </Grid>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  CYCLES
// ═══════════════════════════════════════════════════════════════════════════════
function CyclesScreen() {
  const [showForm, setShowForm] = useState(false);
  const cycles = [
    {n:"April 2025",    budget:5000, spent:3200, state:"Active", s:"2025-04-01", e:"2025-04-30"},
    {n:"March 2025",    budget:4500, spent:4800, state:"Past",   s:"2025-03-01", e:"2025-03-31"},
    {n:"February 2025", budget:4000, spent:3700, state:"Past",   s:"2025-02-01", e:"2025-02-28"},
  ];
  return (
    <div>
      <PageTitle title="Budget Cycles" action={<Btn small onClick={()=>setShowForm(!showForm)}>+ New Cycle</Btn>}/>
      {showForm && (
        <FormPanel title="New Budget Cycle" onClose={()=>setShowForm(false)}>
          <Field label="Total Budget (EGP)"><Input type="number" placeholder="0.00"/></Field>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="Start Date"><Input type="date"/></Field>
            <Field label="End Date"><Input type="date"/></Field>
          </Grid>
          <div style={{ display:"flex", gap:8, justifyContent:"flex-end" }}>
            <Btn outline onClick={()=>setShowForm(false)}>Cancel</Btn>
            <Btn>Create Cycle</Btn>
          </div>
        </FormPanel>
      )}
      {cycles.map(cy=>{
        const pct=(cy.spent/cy.budget)*100, over=cy.spent>cy.budget;
        return (
          <Card key={cy.n} style={{ marginBottom:10 }}>
            <Row style={{ marginBottom:10 }}>
              <div>
                <div style={{ fontSize:13, color:C.text, fontWeight:600 }}>{cy.n}</div>
                <div style={{ fontSize:9, color:C.muted, marginTop:2 }}>{cy.s} → {cy.e}</div>
              </div>
              <div style={{ display:"flex", gap:6, alignItems:"center" }}>
                <Badge color={cy.state==="Active"?C.green:C.muted}>{cy.state}</Badge>
                {cy.state==="Active" && <Btn small outline>Close</Btn>}
              </div>
            </Row>
            <Row style={{ fontSize:11, marginBottom:6 }}>
              <span style={{ color:C.sub }}>Spent: <span style={{ color:over?C.red:C.text, fontFamily:"monospace" }}>EGP {cy.spent.toLocaleString()}</span></span>
              <span style={{ color:C.sub }}>Budget: <span style={{ color:C.text, fontFamily:"monospace" }}>EGP {cy.budget.toLocaleString()}</span></span>
            </Row>
            <Bar pct={pct}/>
            {over && <div style={{ fontSize:9, color:C.red, marginTop:4 }}>⚠ Over budget by EGP {(cy.spent-cy.budget).toLocaleString()}</div>}
          </Card>
        );
      })}
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  SAVING GOALS
// ═══════════════════════════════════════════════════════════════════════════════
function GoalsScreen() {
  const [showForm, setShowForm] = useState(false);
  const [addTo, setAddTo] = useState(null);
  const goals = [
    {n:"New Laptop",      cur:3200,  tgt:8000,  date:"2025-09-01", s:"Active",    c:C.pink},
    {n:"Summer Vacation", cur:1500,  tgt:5000,  date:"2025-07-01", s:"Active",    c:C.green},
    {n:"Emergency Fund",  cur:10000, tgt:10000, date:null,         s:"Completed", c:C.amber},
  ];
  return (
    <div>
      <PageTitle title="Saving Goals" action={<Btn small onClick={()=>setShowForm(!showForm)}>+ New Goal</Btn>}/>
      {showForm && (
        <FormPanel title="New Saving Goal" onClose={()=>setShowForm(false)}>
          <Field label="Goal Name"><Input placeholder='e.g. "New Laptop"'/></Field>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="Target Amount (EGP)"><Input type="number" placeholder="0.00"/></Field>
            <Field label="Target Date (optional)"><Input type="date"/></Field>
          </Grid>
          <div style={{ display:"flex", gap:8, justifyContent:"flex-end" }}>
            <Btn outline onClick={()=>setShowForm(false)}>Cancel</Btn>
            <Btn>Create Goal</Btn>
          </div>
        </FormPanel>
      )}
      {goals.map(g=>{
        const pct=Math.min((g.cur/g.tgt)*100,100);
        return (
          <Card key={g.n} style={{ marginBottom:12 }}>
            <Row style={{ marginBottom:8 }}>
              <div style={{ display:"flex", alignItems:"center", gap:8 }}>
                <div style={{ width:10, height:10, borderRadius:"50%", background:g.c }}/>
                <span style={{ fontSize:13, color:C.text, fontWeight:600 }}>{g.n}</span>
              </div>
              <div style={{ display:"flex", gap:6, alignItems:"center" }}>
                <Badge color={g.s==="Completed"?C.green:C.amber}>{g.s}</Badge>
                {g.s!=="Completed" && (
                  <Btn small outline color={C.green} onClick={()=>setAddTo(addTo===g.n?null:g.n)}>+ Add</Btn>
                )}
              </div>
            </Row>
            {addTo===g.n && (
              <div style={{ background:C.surf2, borderRadius:6, padding:10, marginBottom:10, display:"flex", gap:8, alignItems:"center" }}>
                <Input type="number" placeholder="Amount to add (EGP)" style={{ flex:1 }}/>
                <Btn small>Add</Btn>
                <button onClick={()=>setAddTo(null)} style={{ background:"none", border:"none", color:C.muted, cursor:"pointer", fontSize:16 }}>×</button>
              </div>
            )}
            <Row style={{ fontSize:11, marginBottom:8 }}>
              <span style={{ color:C.sub }}>Saved: <span style={{ color:g.c, fontFamily:"monospace", fontWeight:700 }}>EGP {g.cur.toLocaleString()}</span></span>
              <span style={{ color:C.sub }}>Target: <span style={{ color:C.text, fontFamily:"monospace" }}>EGP {g.tgt.toLocaleString()}</span></span>
            </Row>
            <Bar pct={pct} color={g.c} h={8}/>
            <Row style={{ fontSize:9, color:C.muted, marginTop:4 }}>
              <span>{pct.toFixed(0)}% complete</span>
              {g.date && <span>Target: {g.date}</span>}
            </Row>
          </Card>
        );
      })}
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  SETTINGS
// ═══════════════════════════════════════════════════════════════════════════════
function SettingsScreen({ onLogout }) {
  const [dark, setDark] = useState(true);
  const [newPw, setNewPw] = useState("");
  const [confirmPw, setConfirmPw] = useState("");
  const match = newPw && confirmPw ? newPw===confirmPw : null;

  function Section({ title, children }) {
    return (
      <div style={{ marginBottom:4 }}>
        <Lbl c={C.muted}>{title}</Lbl>
        {children}
      </div>
    );
  }
  return (
    <div>
      <PageTitle title="Settings"/>
      <Card style={{ marginBottom:14, display:"flex", alignItems:"center", gap:14 }}>
        <div style={{ width:44, height:44, borderRadius:"50%", background:C.purple+"44", display:"flex", alignItems:"center", justifyContent:"center", fontSize:14, color:C.purple, fontWeight:700, flexShrink:0 }}>AH</div>
        <div>
          <div style={{ fontSize:14, color:C.text, fontWeight:700 }}>Ahmed Hassan</div>
          <div style={{ fontSize:10, color:C.muted, marginTop:2 }}>@ahmed · Member since Jan 2025</div>
        </div>
      </Card>
      <Card>
        <Section title="Appearance">
          <Row>
            <div>
              <div style={{ fontSize:12, color:C.text, fontWeight:600 }}>Interface Theme</div>
              <div style={{ fontSize:10, color:C.muted, marginTop:2 }}>
                Currently: <span style={{ color:dark?C.blue:C.amber }}>{dark?"Dark Mode":"Light Mode"}</span>
              </div>
            </div>
            <div style={{ display:"flex", alignItems:"center", gap:8 }}>
              <span style={{ fontSize:11, color:C.amber }}>☀</span>
              <Toggle on={dark} onChange={setDark}/>
              <span style={{ fontSize:11, color:C.blue }}>☽</span>
            </div>
          </Row>
        </Section>
        <Divider/>
        <Section title="Security">
          <Field label="Current Password"><Input type="password" placeholder="Enter current password"/></Field>
          <Grid cols="1fr 1fr" gap={10}>
            <Field label="New Password"><Input type="password" placeholder="Min 6 characters" value={newPw} onChange={e=>setNewPw(e.target.value)}/></Field>
            <Field label="Confirm New Password">
              <Input type="password" placeholder="Repeat new password" value={confirmPw} onChange={e=>setConfirmPw(e.target.value)}
                style={{ borderColor: match===false?C.red:match===true?C.green:C.border }}/>
            </Field>
          </Grid>
          {match===false && <div style={{ fontSize:10, color:C.red, marginBottom:8 }}>⚠ Passwords do not match</div>}
          {match===true  && <div style={{ fontSize:10, color:C.green, marginBottom:8 }}>✓ Passwords match</div>}
          <Btn full>Update Password</Btn>
        </Section>
        <Divider/>
        <Section title="Session">
          <Row>
            <div>
              <div style={{ fontSize:12, color:C.text, fontWeight:600 }}>Sign Out</div>
              <div style={{ fontSize:10, color:C.muted, marginTop:2 }}>You will be returned to the login screen</div>
            </div>
            <Btn danger onClick={onLogout}>Logout</Btn>
          </Row>
        </Section>
      </Card>
    </div>
  );
}

// ═══════════════════════════════════════════════════════════════════════════════
//  APP SHELL
// ═══════════════════════════════════════════════════════════════════════════════
const NAV = [
  {id:"dashboard",   icon:"⊞", label:"Dashboard"},
  {id:"transactions",icon:"↕", label:"Transactions"},
  {id:"wallets",     icon:"◈", label:"Wallets"},
  {id:"categories",  icon:"◉", label:"Categories"},
  {id:"cycles",      icon:"◎", label:"Cycles"},
  {id:"goals",       icon:"◇", label:"Goals"},
  {id:"settings",    icon:"⚙", label:"Settings"},
];

export default function App() {
  const [screen, setScreen] = useState("login");
  const [nav, setNav]       = useState("dashboard");
  function logout() { setScreen("login"); setNav("dashboard"); }

  if (screen==="login")  return <LoginScreen  onLogin={()=>setScreen("app")}     onGoSignUp={()=>setScreen("signup")}/>;
  if (screen==="signup") return <SignUpScreen  onSignUp={()=>setScreen("app")}    onGoLogin={()=>setScreen("login")}/>;

  return (
    <div style={{ background:C.bg, minHeight:"100vh", display:"flex", alignItems:"center", justifyContent:"center", padding:20 }}>
      <div style={{ width:"100%", maxWidth:860 }}>
        <div style={{ display:"flex", alignItems:"baseline", gap:10, marginBottom:14 }}>
          <div style={{ fontSize:20, fontWeight:900, color:C.green, letterSpacing:"-0.04em" }}>$penderman</div>
          <div style={{ fontSize:10, color:C.muted }}>Personal Finance</div>
        </div>
        <div style={{ border:`1px solid ${C.border}`, borderRadius:12, overflow:"hidden", height:600, display:"flex" }}>
          {/* Sidebar */}
          <div style={{ width:185, background:C.surf, borderRight:`1px solid ${C.border}`, display:"flex", flexDirection:"column", flexShrink:0 }}>
            <div style={{ padding:"14px 16px 12px", borderBottom:`1px solid ${C.border}` }}>
              <div style={{ fontSize:14, fontWeight:900, color:C.green, letterSpacing:"-0.04em" }}>$penderman</div>
              <div style={{ fontSize:9, color:C.muted, marginTop:2 }}>Personal Finance</div>
            </div>
            <div style={{ padding:"8px 0", flex:1, overflowY:"auto" }}>
              {NAV.map(item=>(
                <button key={item.id} onClick={()=>setNav(item.id)} style={{
                  display:"flex", alignItems:"center", gap:10, padding:"7px 16px",
                  width:"100%", background:nav===item.id?C.green+"15":"transparent",
                  border:"none", borderLeft:nav===item.id?`3px solid ${C.green}`:"3px solid transparent",
                  cursor:"pointer", color:nav===item.id?C.green:C.sub, fontSize:12, transition:"all 0.12s",
                }}>
                  <span style={{ fontSize:13 }}>{item.icon}</span>{item.label}
                </button>
              ))}
            </div>
            <div style={{ padding:"10px 16px", borderTop:`1px solid ${C.border}`, display:"flex", alignItems:"center", gap:8 }}>
              <div style={{ width:28, height:28, borderRadius:"50%", background:C.purple+"44", display:"flex", alignItems:"center", justifyContent:"center", fontSize:10, color:C.purple, fontWeight:700 }}>AH</div>
              <div>
                <div style={{ fontSize:11, color:C.text }}>Ahmed</div>
                <div style={{ fontSize:9, color:C.muted }}>🌙 Dark Mode</div>
              </div>
            </div>
          </div>
          {/* Content */}
          <div style={{ flex:1, overflowY:"auto", background:C.bg, padding:16 }}>
            {nav==="dashboard"    && <DashboardScreen/>}
            {nav==="transactions" && <TransactionsScreen/>}
            {nav==="wallets"      && <WalletsScreen/>}
            {nav==="categories"   && <CategoriesScreen/>}
            {nav==="cycles"       && <CyclesScreen/>}
            {nav==="goals"        && <GoalsScreen/>}
            {nav==="settings"     && <SettingsScreen onLogout={logout}/>}
          </div>
        </div>
      </div>
    </div>
  );
}
