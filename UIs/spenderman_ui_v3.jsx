import { useState } from "react";

// ─── COLOR PALETTE (unchanged from v2) ───────────────────────────────────────
const C = {
  bg:     "#080B12", surf:   "#0F1319", surf2:  "#161B26", surf3:  "#1D2333",
  border: "#252D40", green:  "#22C97A", purple: "#8875F5", blue:   "#4B9EF8",
  amber:  "#F5A623", red:    "#F56565", pink:   "#F472B6",
  text:   "#DCE1EE", sub:    "#8490A8", muted:  "#4A5268",
};

// ─── SHARED HELPERS ───────────────────────────────────────────────────────────
function Badge({ label, color, bg }) {
  return (
    <span style={{
      background: bg || color + "22", color: color,
      borderRadius: 4, padding: "2px 8px", fontSize: 10, fontWeight: 600,
    }}>{label}</span>
  );
}

function SectionTitle({ children, action }) {
  return (
    <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center", marginBottom:16 }}>
      <div style={{ fontSize:14, fontWeight:800, color:C.text, letterSpacing:"-0.02em" }}>{children}</div>
      {action}
    </div>
  );
}

function AddBtn({ label, onClick }) {
  return (
    <button onClick={onClick} style={{
      background: C.green, color: "#000", border: "none", borderRadius: 6,
      padding: "6px 14px", fontSize: 11, cursor: "pointer", fontWeight: 800,
      letterSpacing: "0.02em",
    }}>+ {label}</button>
  );
}

function Card({ children, style }) {
  return (
    <div style={{
      background: C.surf, border: `1px solid ${C.border}`,
      borderRadius: 10, padding: 14, ...style,
    }}>{children}</div>
  );
}

function FormRow({ label, children }) {
  return (
    <div style={{ display:"flex", flexDirection:"column", gap:4 }}>
      <label style={{ fontSize:10, color:C.muted, fontWeight:600, textTransform:"uppercase", letterSpacing:"0.08em" }}>{label}</label>
      {children}
    </div>
  );
}

const inputStyle = {
  background: C.surf2, border: `1px solid ${C.border}`, borderRadius: 6,
  padding: "7px 10px", color: C.text, fontSize: 11, outline: "none", width: "100%",
  boxSizing: "border-box",
};

const selectStyle = { ...inputStyle, cursor: "pointer" };

// ─── DONUT CHART ─────────────────────────────────────────────────────────────
function DonutChart({ segments, size = 110, strokeWidth = 20, centerLabel, centerSub }) {
  const cx = size / 2, cy = size / 2, r = (size - strokeWidth) / 2;
  let cum = 0;
  const arcs = segments.map(s => {
    const a1 = (cum / 100) * 2 * Math.PI - Math.PI / 2;
    cum += s.pct;
    const a2 = (cum / 100) * 2 * Math.PI - Math.PI / 2;
    const x1 = cx + r * Math.cos(a1), y1 = cy + r * Math.sin(a1);
    const x2 = cx + r * Math.cos(a2), y2 = cy + r * Math.sin(a2);
    const large = s.pct > 50 ? 1 : 0;
    return { ...s, d: `M${x1.toFixed(2)} ${y1.toFixed(2)} A${r} ${r} 0 ${large} 1 ${x2.toFixed(2)} ${y2.toFixed(2)}` };
  });

  return (
    <svg width={size} height={size} viewBox={`0 0 ${size} ${size}`} style={{ flexShrink: 0 }}>
      {arcs.map((s, i) => (
        <path key={i} d={s.d} fill="none" stroke={s.color} strokeWidth={strokeWidth} strokeLinecap="butt" />
      ))}
      <circle cx={cx} cy={cy} r={r - strokeWidth / 2 - 1} fill={C.bg} />
      {centerLabel && (
        <>
          <text x={cx} y={cy - 4} textAnchor="middle" fill={C.text} fontSize="11" fontWeight="800">{centerLabel}</text>
          {centerSub && <text x={cx} y={cy + 9} textAnchor="middle" fill={C.muted} fontSize="8">{centerSub}</text>}
        </>
      )}
    </svg>
  );
}

function ChartLegend({ segments }) {
  return (
    <div style={{ display:"flex", flexDirection:"column", gap:5, flex:1 }}>
      {segments.map(s => (
        <div key={s.label} style={{ display:"flex", alignItems:"center", gap:7 }}>
          <div style={{ width:8, height:8, borderRadius:2, background:s.color, flexShrink:0 }} />
          <div style={{ fontSize:10, color:C.sub, flex:1, whiteSpace:"nowrap", overflow:"hidden", textOverflow:"ellipsis" }}>{s.label}</div>
          <div style={{ fontSize:10, color:C.text, fontFamily:"monospace" }}>{s.pct}%</div>
        </div>
      ))}
    </div>
  );
}

// ─── DASHBOARD SCREEN ─────────────────────────────────────────────────────────
function DashboardScreen() {
  const [hasCycle, setHasCycle] = useState(true);

  const expenseSegs = [
    { label:"Food",          pct:35, color:C.amber  },
    { label:"Transport",     pct:20, color:C.purple  },
    { label:"Utilities",     pct:15, color:C.pink    },
    { label:"Entertainment", pct:18, color:C.blue    },
    { label:"Other",         pct:12, color:C.muted   },
  ];
  const depositSegs = [
    { label:"Salary",        pct:70, color:C.green   },
    { label:"Freelance",     pct:20, color:C.blue    },
    { label:"Gifts",         pct:10, color:C.pink    },
  ];
  const walletSegs = [
    { label:"Main Bank",     pct:52, color:C.green   },
    { label:"Business",      pct:40, color:C.purple  },
    { label:"Pocket Cash",   pct: 8, color:C.blue    },
  ];

  const cycleSpent  = 3200, cycleBudget = 5000;
  const cyclePct    = Math.round((cycleSpent / cycleBudget) * 100);
  const cycleRemain = cycleBudget - cycleSpent;
  const daysLeft    = 15;
  const safeDaily   = (cycleRemain / daysLeft).toFixed(2);

  return (
    <div>
      <SectionTitle>Dashboard</SectionTitle>

      {/* ── Total Balance ── */}
      <Card style={{ marginBottom:14, background:"linear-gradient(135deg,#0F1319 60%,#161B26)" }}>
        <div style={{ fontSize:10, color:C.muted, textTransform:"uppercase", letterSpacing:"0.1em", marginBottom:6 }}>Total Balance — All Wallets</div>
        <div style={{ fontSize:28, fontWeight:900, color:C.green, fontFamily:"'DM Mono','Courier New',monospace", letterSpacing:"-0.03em" }}>
          EGP 58,450.00
        </div>
        <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:8, marginTop:12 }}>
          {[
            {n:"Main Bank",      b:"12,400.00", c:C.green  },
            {n:"Pocket Cash",    b:"850.00",    c:C.blue   },
            {n:"Business",       b:"45,200.00", c:C.purple },
          ].map(w => (
            <div key={w.n} style={{ background:C.surf3, borderRadius:7, padding:"8px 10px" }}>
              <div style={{ fontSize:9, color:C.muted, marginBottom:2 }}>{w.n}</div>
              <div style={{ fontSize:12, fontWeight:800, color:w.c, fontFamily:"monospace" }}>{w.b}</div>
            </div>
          ))}
        </div>
      </Card>

      {/* ── Cycle Panel ── */}
      <Card style={{ marginBottom:14 }}>
        <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center", marginBottom:10 }}>
          <div style={{ fontSize:10, color:C.sub, textTransform:"uppercase", letterSpacing:"0.08em" }}>Budget Cycle</div>
          <div style={{ display:"flex", gap:6, alignItems:"center" }}>
            <Badge label="ACTIVE" color={C.green} />
            <button onClick={() => setHasCycle(!hasCycle)} style={{ background:C.surf3, border:`1px solid ${C.border}`, color:C.muted, borderRadius:4, padding:"2px 8px", fontSize:9, cursor:"pointer" }}>
              {hasCycle ? "simulate no cycle" : "simulate active"}
            </button>
          </div>
        </div>

        {hasCycle ? (
          <>
            <div style={{ fontSize:11, color:C.sub, marginBottom:10 }}>April 2025 · 2025-04-01 → 2025-04-30</div>
            <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:8, marginBottom:12 }}>
              {[
                { label:"Budget",    val:`EGP ${cycleBudget.toLocaleString()}`, color:C.text  },
                { label:"Spent",     val:`EGP ${cycleSpent.toLocaleString()}`,  color:C.amber },
                { label:"Remaining", val:`EGP ${cycleRemain.toLocaleString()}`, color:C.green },
              ].map(item => (
                <div key={item.label} style={{ background:C.surf2, borderRadius:7, padding:"8px 10px" }}>
                  <div style={{ fontSize:9, color:C.muted, marginBottom:2 }}>{item.label}</div>
                  <div style={{ fontSize:13, fontWeight:800, color:item.color, fontFamily:"monospace" }}>{item.val}</div>
                </div>
              ))}
            </div>
            <div style={{ background:C.surf3, borderRadius:6, height:8, marginBottom:6 }}>
              <div style={{
                width:`${cyclePct}%`, height:"100%", borderRadius:6,
                background: cyclePct > 85 ? C.red : cyclePct > 65 ? C.amber : C.green,
                transition:"width 0.4s ease",
              }} />
            </div>
            <div style={{ display:"flex", justifyContent:"space-between", fontSize:10, color:C.muted }}>
              <span>{cyclePct}% of budget used</span>
              <span style={{ color:C.blue }}>⚡ Safe daily limit: <span style={{ color:C.text, fontFamily:"monospace", fontWeight:700 }}>EGP {safeDaily}</span> · {daysLeft} days left</span>
            </div>
          </>
        ) : (
          <div style={{ textAlign:"center", padding:"20px 0" }}>
            <div style={{ fontSize:28, marginBottom:8 }}>◎</div>
            <div style={{ fontSize:13, color:C.sub, marginBottom:4 }}>No active budget cycle</div>
            <div style={{ fontSize:10, color:C.muted, marginBottom:14 }}>Create a cycle to start tracking your spending against a budget.</div>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>
              + Create Cycle
            </button>
          </div>
        )}
      </Card>

      {/* ── Three Pie Charts ── */}
      <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:12 }}>
        {[
          { title:"Expenses by Category", segs:expenseSegs,  center:"35%",    csub:"Food"       },
          { title:"Deposits by Category", segs:depositSegs,  center:"70%",    csub:"Salary"     },
          { title:"Wallets by Balance",   segs:walletSegs,   center:"EGP",    csub:"3 wallets"  },
        ].map(chart => (
          <Card key={chart.title}>
            <div style={{ fontSize:10, color:C.sub, marginBottom:10, textTransform:"uppercase", letterSpacing:"0.07em" }}>{chart.title}</div>
            <div style={{ display:"flex", alignItems:"center", gap:10 }}>
              <DonutChart segments={chart.segs} size={100} strokeWidth={18} centerLabel={chart.center} centerSub={chart.csub} />
              <ChartLegend segments={chart.segs} />
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}

// ─── TRANSACTIONS SCREEN ──────────────────────────────────────────────────────
function TransactionsScreen() {
  const [showForm, setShowForm] = useState(false);
  const [txType, setTxType] = useState("EXPENSE");
  const [target, setTarget] = useState("WALLET"); // "WALLET" | "GOAL"

  const txRows = [
    { note:"Lunch at Koshary",   target:"Pocket Cash",   catLabel:"Food",        type:"EXPENSE", amount:-85,    date:"2025-04-15", targetType:"wallet" },
    { note:"April Salary",       target:"Main Bank",     catLabel:"Salary",      type:"DEPOSIT", amount:8500,   date:"2025-04-01", targetType:"wallet" },
    { note:"Uber ride",          target:"Pocket Cash",   catLabel:"Transport",   type:"EXPENSE", amount:-45,    date:"2025-04-14", targetType:"wallet" },
    { note:"New Laptop fund",    target:"New Laptop 💻",  catLabel:"Savings",     type:"EXPENSE", amount:-500,   date:"2025-04-10", targetType:"goal"   },
    { note:"Electricity bill",   target:"Main Bank",     catLabel:"Utilities",   type:"EXPENSE", amount:-320,   date:"2025-04-05", targetType:"wallet" },
    { note:"Freelance payment",  target:"Business",      catLabel:"Freelance",   type:"DEPOSIT", amount:4200,   date:"2025-04-03", targetType:"wallet" },
  ];

  return (
    <div>
      <SectionTitle action={<AddBtn label="Add Transaction" onClick={() => setShowForm(!showForm)} />}>
        Transactions
      </SectionTitle>

      {/* ── Add Form ── */}
      {showForm && (
        <Card style={{ marginBottom:14 }}>
          <div style={{ fontSize:11, fontWeight:700, color:C.text, marginBottom:12 }}>New Transaction</div>
          <div style={{ display:"grid", gridTemplateColumns:"1fr 1fr", gap:10, marginBottom:10 }}>
            <FormRow label="Amount (EGP)">
              <input style={inputStyle} placeholder="0.00" type="number" />
            </FormRow>
            <FormRow label="Type">
              <select style={selectStyle} value={txType} onChange={e => setTxType(e.target.value)}>
                <option value="EXPENSE">EXPENSE</option>
                <option value="DEPOSIT">DEPOSIT</option>
              </select>
            </FormRow>
          </div>

          {/* Target toggle — Wallet OR Goal */}
          <div style={{ marginBottom:10 }}>
            <div style={{ fontSize:10, color:C.muted, fontWeight:600, textTransform:"uppercase", letterSpacing:"0.08em", marginBottom:6 }}>
              Target (exactly one required)
            </div>
            <div style={{ display:"flex", gap:0, borderRadius:6, overflow:"hidden", border:`1px solid ${C.border}`, width:"fit-content", marginBottom:8 }}>
              {["WALLET","GOAL"].map(t => (
                <button key={t} onClick={() => setTarget(t)} style={{
                  padding:"5px 16px", border:"none", fontSize:11, cursor:"pointer", fontWeight:700,
                  background: target === t ? C.green : C.surf2,
                  color: target === t ? "#000" : C.muted,
                  transition:"all 0.15s",
                }}>{t}</button>
              ))}
            </div>
            {target === "WALLET" ? (
              <select style={selectStyle}>
                <option>Main Bank Account</option>
                <option>Pocket Cash</option>
                <option>Business Account</option>
              </select>
            ) : (
              <select style={selectStyle}>
                <option>New Laptop 💻</option>
                <option>Summer Vacation 🏖</option>
              </select>
            )}
            <div style={{ fontSize:9, color:C.muted, marginTop:4 }}>
              {target === "WALLET"
                ? "Wallet balance will be updated automatically."
                : "Funds will be deposited directly into the saving goal."}
            </div>
          </div>

          <div style={{ display:"grid", gridTemplateColumns:"1fr 1fr", gap:10, marginBottom:10 }}>
            <FormRow label="Category">
              <select style={selectStyle}>
                <option>Food</option>
                <option>Transport</option>
                <option>Utilities</option>
                <option>Entertainment</option>
                <option>Salary</option>
                <option>Freelance</option>
              </select>
            </FormRow>
            <FormRow label="Date">
              <input style={inputStyle} type="date" defaultValue="2025-04-25" />
            </FormRow>
          </div>
          <FormRow label="Note (optional)">
            <input style={inputStyle} placeholder="e.g. Dinner at Cairo Festival City" />
          </FormRow>
          <div style={{ display:"flex", gap:8, marginTop:12 }}>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>
              Save Transaction
            </button>
            <button onClick={() => setShowForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>
              Cancel
            </button>
          </div>
        </Card>
      )}

      {/* ── Table ── */}
      <Card style={{ padding:0, overflow:"hidden" }}>
        <table style={{ width:"100%", borderCollapse:"collapse", fontSize:11 }}>
          <thead>
            <tr style={{ background:C.surf2 }}>
              {["Note","Target","Category","Type","Amount","Date",""].map(h => (
                <th key={h} style={{ padding:"9px 12px", textAlign:"left", color:C.muted, fontWeight:600, fontSize:10, textTransform:"uppercase", letterSpacing:"0.06em" }}>{h}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {txRows.map((t, i) => (
              <tr key={i} style={{ borderTop:`1px solid ${C.border}22` }}>
                <td style={{ padding:"8px 12px", color:C.text }}>{t.note}</td>
                <td style={{ padding:"8px 12px" }}>
                  <div style={{ display:"flex", alignItems:"center", gap:5 }}>
                    <span style={{ fontSize:8, fontWeight:700, color: t.targetType==="goal" ? C.pink : C.blue, background: t.targetType==="goal" ? C.pink+"22" : C.blue+"22", borderRadius:3, padding:"1px 5px" }}>
                      {t.targetType === "goal" ? "GOAL" : "WALLET"}
                    </span>
                    <span style={{ color:C.sub }}>{t.target}</span>
                  </div>
                </td>
                <td style={{ padding:"8px 12px", color:C.sub }}>{t.catLabel}</td>
                <td style={{ padding:"8px 12px" }}>
                  <Badge label={t.type} color={t.type==="DEPOSIT" ? C.green : C.red} />
                </td>
                <td style={{ padding:"8px 12px", color:t.amount>0?C.green:C.red, fontFamily:"monospace", fontWeight:700 }}>
                  {t.amount>0?"+":""}{t.amount.toLocaleString()} EGP
                </td>
                <td style={{ padding:"8px 12px", color:C.muted }}>{t.date}</td>
                <td style={{ padding:"8px 12px" }}>
                  <button style={{ background:"transparent", border:"none", color:C.red+"88", cursor:"pointer", fontSize:12 }}>✕</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </Card>
    </div>
  );
}

// ─── WALLETS SCREEN ───────────────────────────────────────────────────────────
function WalletsScreen() {
  const [showForm, setShowForm] = useState(false);
  const wallets = [
    { n:"Main Bank Account",  b:"12,400.00", tx:24, c:C.green,  icon:"🏦" },
    { n:"Pocket Cash",        b:"850.00",    tx:8,  c:C.blue,   icon:"👛" },
    { n:"Business Account",   b:"45,200.00", tx:12, c:C.purple, icon:"💼" },
  ];

  return (
    <div>
      <SectionTitle action={<AddBtn label="New Wallet" onClick={() => setShowForm(!showForm)} />}>
        Wallets
      </SectionTitle>

      {showForm && (
        <Card style={{ marginBottom:14 }}>
          <div style={{ fontSize:11, fontWeight:700, color:C.text, marginBottom:12 }}>New Wallet</div>
          <FormRow label="Wallet Name">
            <input style={inputStyle} placeholder='e.g. "Mobile Wallet", "CIB Account"' />
          </FormRow>
          <div style={{ fontSize:9, color:C.muted, marginTop:6 }}>New wallets start with a balance of EGP 0.00. Use a Deposit transaction to add funds.</div>
          <div style={{ display:"flex", gap:8, marginTop:12 }}>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>Create</button>
            <button onClick={() => setShowForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>Cancel</button>
          </div>
        </Card>
      )}

      <div style={{ display:"grid", gridTemplateColumns:"repeat(2,1fr)", gap:12 }}>
        {wallets.map(w => (
          <Card key={w.n}>
            <div style={{ display:"flex", justifyContent:"space-between", alignItems:"flex-start", marginBottom:12 }}>
              <div style={{ width:40, height:40, borderRadius:10, background:w.c+"22", display:"flex", alignItems:"center", justifyContent:"center", fontSize:20 }}>{w.icon}</div>
              <button style={{ background:"transparent", border:"none", color:C.red+"88", cursor:"pointer", fontSize:12 }}>✕</button>
            </div>
            <div style={{ fontSize:11, color:C.sub, marginBottom:4 }}>{w.n}</div>
            <div style={{ fontSize:22, fontWeight:900, color:w.c, fontFamily:"monospace", letterSpacing:"-0.02em" }}>EGP {w.b}</div>
            <div style={{ marginTop:12, display:"flex", justifyContent:"space-between", alignItems:"center" }}>
              <div style={{ fontSize:10, color:C.muted }}>{w.tx} transactions</div>
              <div style={{ width:40, height:4, borderRadius:2, background:w.c + "44" }}>
                <div style={{ width:"100%", height:"100%", borderRadius:2, background:w.c }} />
              </div>
            </div>
          </Card>
        ))}
      </div>
    </div>
  );
}

// ─── CATEGORIES SCREEN ────────────────────────────────────────────────────────
function CategoriesScreen() {
  const [showForm, setShowForm] = useState(false);
  const [pickedColor, setPickedColor] = useState("#22C97A");
  const [catType, setCatType] = useState("EXPENSE");

  const cats = [
    { n:"Food",          c:"#F5A623", t:"EXPENSE", count:18 },
    { n:"Transport",     c:C.purple,  t:"EXPENSE", count:7  },
    { n:"Salary",        c:C.green,   t:"DEPOSIT", count:2  },
    { n:"Utilities",     c:C.pink,    t:"EXPENSE", count:4  },
    { n:"Entertainment", c:C.blue,    t:"EXPENSE", count:6  },
    { n:"Freelance",     c:"#F5A623", t:"DEPOSIT", count:3  },
  ];

  const colors = [C.green, C.blue, C.purple, C.amber, C.red, C.pink, "#06B6D4", "#84CC16", "#F97316", "#A855F7"];

  return (
    <div>
      <SectionTitle action={<AddBtn label="New Category" onClick={() => setShowForm(!showForm)} />}>
        Categories
      </SectionTitle>

      {showForm && (
        <Card style={{ marginBottom:14 }}>
          <div style={{ fontSize:11, fontWeight:700, color:C.text, marginBottom:12 }}>New Category</div>
          <div style={{ display:"grid", gridTemplateColumns:"1fr 1fr", gap:10, marginBottom:10 }}>
            <FormRow label="Name">
              <input style={inputStyle} placeholder='e.g. "Groceries"' />
            </FormRow>
            <FormRow label="Type">
              <select style={selectStyle} value={catType} onChange={e => setCatType(e.target.value)}>
                <option value="EXPENSE">EXPENSE</option>
                <option value="DEPOSIT">DEPOSIT</option>
              </select>
            </FormRow>
          </div>
          <FormRow label="Color">
            <div style={{ display:"flex", gap:6, flexWrap:"wrap", marginTop:2 }}>
              {colors.map(col => (
                <button key={col} onClick={() => setPickedColor(col)} style={{
                  width:24, height:24, borderRadius:6, background:col, border:"none", cursor:"pointer",
                  outline: pickedColor === col ? `2px solid ${C.text}` : "2px solid transparent",
                  outlineOffset:2,
                }} />
              ))}
              <input type="color" value={pickedColor} onChange={e => setPickedColor(e.target.value)} style={{ width:24, height:24, borderRadius:6, border:"none", cursor:"pointer", padding:0, background:"transparent" }} />
            </div>
            <div style={{ marginTop:6, display:"flex", alignItems:"center", gap:8 }}>
              <div style={{ width:28, height:28, borderRadius:8, background:pickedColor }} />
              <span style={{ fontSize:10, color:C.sub, fontFamily:"monospace" }}>{pickedColor}</span>
            </div>
          </FormRow>
          <div style={{ display:"flex", gap:8, marginTop:12 }}>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>Create</button>
            <button onClick={() => setShowForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>Cancel</button>
          </div>
        </Card>
      )}

      <div style={{ marginBottom:10, display:"flex", gap:8 }}>
        {["ALL","EXPENSE","DEPOSIT"].map(f => (
          <button key={f} style={{ background:C.surf, border:`1px solid ${C.border}`, color:C.sub, borderRadius:5, padding:"3px 10px", fontSize:10, cursor:"pointer" }}>{f}</button>
        ))}
      </div>

      <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:10 }}>
        {cats.map(cat => (
          <Card key={cat.n} style={{ display:"flex", alignItems:"center", gap:10, padding:12 }}>
            <div style={{ width:36, height:36, borderRadius:10, background:cat.c, flexShrink:0 }} />
            <div style={{ flex:1, minWidth:0 }}>
              <div style={{ fontSize:12, color:C.text, fontWeight:600 }}>{cat.n}</div>
              <div style={{ fontSize:9, marginTop:2, color:cat.t==="DEPOSIT"?C.green:C.red }}>{cat.t} · {cat.count} txns</div>
            </div>
            <button style={{ background:"transparent", border:"none", color:C.red+"88", cursor:"pointer", fontSize:12, flexShrink:0 }}>✕</button>
          </Card>
        ))}
      </div>
    </div>
  );
}

// ─── CYCLES SCREEN ────────────────────────────────────────────────────────────
function CyclesScreen() {
  const [showForm, setShowForm] = useState(false);

  const cycles = [
    { n:"April 2025",    budget:5000, spent:3200, state:"ACTIVE", s:"2025-04-01", e:"2025-04-30" },
    { n:"March 2025",    budget:4500, spent:4800, state:"PAST",   s:"2025-03-01", e:"2025-03-31" },
    { n:"February 2025", budget:4000, spent:3700, state:"PAST",   s:"2025-02-01", e:"2025-02-28" },
  ];

  return (
    <div>
      <SectionTitle action={<AddBtn label="New Cycle" onClick={() => setShowForm(!showForm)} />}>
        Budget Cycles
      </SectionTitle>

      {showForm && (
        <Card style={{ marginBottom:14 }}>
          <div style={{ fontSize:11, fontWeight:700, color:C.text, marginBottom:12 }}>New Budget Cycle</div>
          <div style={{ fontSize:9, color:C.amber, background:C.amber+"15", borderRadius:6, padding:"6px 10px", marginBottom:12 }}>
            ⚠ Creating a new cycle will automatically archive any currently active cycle.
          </div>
          <div style={{ display:"grid", gridTemplateColumns:"1fr 1fr 1fr", gap:10, marginBottom:10 }}>
            <FormRow label="Budget Amount (EGP)">
              <input style={inputStyle} placeholder="5000.00" type="number" />
            </FormRow>
            <FormRow label="Start Date">
              <input style={inputStyle} type="date" />
            </FormRow>
            <FormRow label="End Date">
              <input style={inputStyle} type="date" />
            </FormRow>
          </div>
          <div style={{ display:"flex", gap:8 }}>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>Create</button>
            <button onClick={() => setShowForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>Cancel</button>
          </div>
        </Card>
      )}

      <div style={{ display:"flex", flexDirection:"column", gap:10 }}>
        {cycles.map(cy => {
          const pct  = Math.min((cy.spent / cy.budget) * 100, 100);
          const over = cy.spent > cy.budget;
          const barColor = over ? C.red : pct > 80 ? C.amber : C.green;
          return (
            <Card key={cy.n}>
              <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center", marginBottom:10 }}>
                <div>
                  <div style={{ fontSize:13, color:C.text, fontWeight:700 }}>{cy.n}</div>
                  <div style={{ fontSize:10, color:C.muted, marginTop:1 }}>{cy.s} → {cy.e}</div>
                </div>
                <div style={{ display:"flex", gap:8, alignItems:"center" }}>
                  <Badge label={cy.state} color={cy.state==="ACTIVE" ? C.green : C.muted} />
                  {cy.state === "ACTIVE" && (
                    <button style={{ background:C.surf3, border:`1px solid ${C.border}`, color:C.amber, borderRadius:5, padding:"3px 8px", fontSize:10, cursor:"pointer" }}>
                      Close Cycle
                    </button>
                  )}
                </div>
              </div>
              <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:8, marginBottom:10 }}>
                {[
                  {l:"Budget",    v:`EGP ${cy.budget.toLocaleString()}`, c:C.text  },
                  {l:"Spent",     v:`EGP ${cy.spent.toLocaleString()}`,  c:over?C.red:C.amber },
                  {l:"Remaining", v:`EGP ${Math.max(cy.budget-cy.spent,0).toLocaleString()}`, c:C.green },
                ].map(item => (
                  <div key={item.l} style={{ background:C.surf2, borderRadius:6, padding:"6px 8px" }}>
                    <div style={{ fontSize:9, color:C.muted }}>{item.l}</div>
                    <div style={{ fontSize:11, fontWeight:800, color:item.c, fontFamily:"monospace" }}>{item.v}</div>
                  </div>
                ))}
              </div>
              <div style={{ background:C.surf3, borderRadius:4, height:6 }}>
                <div style={{ width:`${pct}%`, height:"100%", background:barColor, borderRadius:4 }} />
              </div>
              {over && <div style={{ fontSize:10, color:C.red, marginTop:5 }}>⚠ Over budget by EGP {(cy.spent - cy.budget).toLocaleString()}</div>}
            </Card>
          );
        })}
      </div>
    </div>
  );
}

// ─── GOALS SCREEN ─────────────────────────────────────────────────────────────
function GoalsScreen() {
  const [showForm, setShowForm] = useState(false);

  const goals = [
    { n:"New Laptop 💻",        cur:3200,  tgt:8000,  date:"2025-09-01", s:"ACTIVE",    c:C.pink  },
    { n:"Summer Vacation 🏖",   cur:1500,  tgt:5000,  date:"2025-07-01", s:"ACTIVE",    c:C.green },
    { n:"Emergency Fund 🛡",    cur:10000, tgt:10000, date:null,          s:"COMPLETED", c:C.amber },
    { n:"Gaming PC 🎮",         cur:0,     tgt:15000, date:"2025-12-01", s:"CANCELLED", c:C.muted },
  ];

  return (
    <div>
      <SectionTitle action={<AddBtn label="New Goal" onClick={() => setShowForm(!showForm)} />}>
        Saving Goals
      </SectionTitle>

      {showForm && (
        <Card style={{ marginBottom:14 }}>
          <div style={{ fontSize:11, fontWeight:700, color:C.text, marginBottom:12 }}>New Saving Goal</div>
          <div style={{ display:"grid", gridTemplateColumns:"1fr 1fr 1fr", gap:10, marginBottom:10 }}>
            <FormRow label="Goal Name">
              <input style={inputStyle} placeholder='e.g. "New Car"' />
            </FormRow>
            <FormRow label="Target Amount (EGP)">
              <input style={inputStyle} placeholder="0.00" type="number" />
            </FormRow>
            <FormRow label="Target Date (optional)">
              <input style={inputStyle} type="date" />
            </FormRow>
          </div>
          <div style={{ fontSize:9, color:C.blue, background:C.blue+"15", borderRadius:6, padding:"6px 10px", marginBottom:10 }}>
            💡 To fund this goal, go to <strong>Transactions</strong> → Add Transaction → Target: <strong>GOAL</strong>. Contributions are recorded as real transactions keeping your ledger balanced.
          </div>
          <div style={{ display:"flex", gap:8 }}>
            <button style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>Create</button>
            <button onClick={() => setShowForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>Cancel</button>
          </div>
        </Card>
      )}

      <div style={{ display:"flex", flexDirection:"column", gap:12 }}>
        {goals.map(g => {
          const pct = Math.min((g.cur / g.tgt) * 100, 100);
          const statusColor = { ACTIVE:C.amber, COMPLETED:C.green, CANCELLED:C.muted }[g.s];
          return (
            <Card key={g.n}>
              <div style={{ display:"flex", justifyContent:"space-between", alignItems:"flex-start", marginBottom:10 }}>
                <div style={{ display:"flex", alignItems:"center", gap:8 }}>
                  <div style={{ width:10, height:10, borderRadius:"50%", background:g.c, flexShrink:0 }} />
                  <span style={{ fontSize:13, color:C.text, fontWeight:700 }}>{g.n}</span>
                </div>
                <div style={{ display:"flex", gap:6, alignItems:"center" }}>
                  <Badge label={g.s} color={statusColor} />
                  {g.s === "ACTIVE" && (
                    <button style={{ background:"transparent", border:`1px solid ${C.border}`, color:C.muted, borderRadius:5, padding:"2px 8px", fontSize:10, cursor:"pointer" }}>
                      Cancel Goal
                    </button>
                  )}
                </div>
              </div>

              <div style={{ display:"grid", gridTemplateColumns:"repeat(3,1fr)", gap:8, marginBottom:10 }}>
                <div style={{ background:C.surf2, borderRadius:6, padding:"6px 8px" }}>
                  <div style={{ fontSize:9, color:C.muted }}>Saved</div>
                  <div style={{ fontSize:12, fontWeight:800, color:g.c, fontFamily:"monospace" }}>EGP {g.cur.toLocaleString()}</div>
                </div>
                <div style={{ background:C.surf2, borderRadius:6, padding:"6px 8px" }}>
                  <div style={{ fontSize:9, color:C.muted }}>Target</div>
                  <div style={{ fontSize:12, fontWeight:800, color:C.text, fontFamily:"monospace" }}>EGP {g.tgt.toLocaleString()}</div>
                </div>
                <div style={{ background:C.surf2, borderRadius:6, padding:"6px 8px" }}>
                  <div style={{ fontSize:9, color:C.muted }}>Remaining</div>
                  <div style={{ fontSize:12, fontWeight:800, color:C.sub, fontFamily:"monospace" }}>EGP {Math.max(g.tgt - g.cur, 0).toLocaleString()}</div>
                </div>
              </div>

              <div style={{ background:C.surf3, borderRadius:6, height:8, marginBottom:6 }}>
                <div style={{ width:`${pct}%`, height:"100%", background:g.s==="CANCELLED"?C.muted:g.c, borderRadius:6, transition:"width 0.4s ease" }} />
              </div>
              <div style={{ display:"flex", justifyContent:"space-between", fontSize:10, color:C.muted }}>
                <span>{pct.toFixed(0)}% complete</span>
                {g.date && <span>Target date: {g.date}</span>}
              </div>

              {g.s === "ACTIVE" && (
                <div style={{ marginTop:10, background:C.blue+"12", borderRadius:6, padding:"6px 10px", fontSize:9, color:C.blue }}>
                  💡 Fund via <strong>Transactions → Add Transaction → Target: GOAL</strong>
                </div>
              )}
            </Card>
          );
        })}
      </div>
    </div>
  );
}

// ─── SETTINGS SCREEN ──────────────────────────────────────────────────────────
function SettingsScreen({ theme, setTheme }) {
  const [showPwForm, setShowPwForm] = useState(false);
  const [feedback, setFeedback] = useState(null);

  const handlePwSave = () => {
    setFeedback({ ok:true, msg:"Password changed successfully." });
    setTimeout(() => { setFeedback(null); setShowPwForm(false); }, 2500);
  };

  return (
    <div>
      <SectionTitle>Settings</SectionTitle>

      {/* ── Profile info ── */}
      <Card style={{ marginBottom:12 }}>
        <div style={{ display:"flex", alignItems:"center", gap:14, marginBottom:0 }}>
          <div style={{ width:52, height:52, borderRadius:"50%", background:C.purple+"44", display:"flex", alignItems:"center", justifyContent:"center", fontSize:18, color:C.purple, fontWeight:900 }}>AH</div>
          <div>
            <div style={{ fontSize:15, fontWeight:800, color:C.text }}>Ahmed Hassan</div>
            <div style={{ fontSize:11, color:C.sub }}>@ahmed_h · Member since Jan 2025</div>
          </div>
        </div>
      </Card>

      {/* ── Theme Toggle ── */}
      <Card style={{ marginBottom:12 }}>
        <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center" }}>
          <div>
            <div style={{ fontSize:13, fontWeight:700, color:C.text, marginBottom:2 }}>Theme</div>
            <div style={{ fontSize:10, color:C.muted }}>Switch between dark and light appearance.</div>
          </div>
          <div style={{ display:"flex", gap:0, borderRadius:8, overflow:"hidden", border:`1px solid ${C.border}` }}>
            {[
              { v:"DARK",  icon:"🌙", label:"Dark"  },
              { v:"LIGHT", icon:"☀️", label:"Light" },
            ].map(t => (
              <button key={t.v} onClick={() => setTheme(t.v)} style={{
                padding:"7px 16px", border:"none", fontSize:11, cursor:"pointer", fontWeight:700,
                background: theme === t.v ? C.green : C.surf2,
                color: theme === t.v ? "#000" : C.muted,
                display:"flex", alignItems:"center", gap:5,
                transition:"all 0.15s",
              }}>
                <span>{t.icon}</span>{t.label}
              </button>
            ))}
          </div>
        </div>
        <div style={{ marginTop:10, fontSize:9, color:C.muted }}>
          Currently: <span style={{ color:C.green, fontWeight:700 }}>{theme} MODE</span> — preference is saved to your account.
        </div>
      </Card>

      {/* ── Change Password ── */}
      <Card style={{ marginBottom:12 }}>
        <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center", marginBottom: showPwForm ? 12 : 0 }}>
          <div>
            <div style={{ fontSize:13, fontWeight:700, color:C.text, marginBottom:2 }}>Change Password</div>
            <div style={{ fontSize:10, color:C.muted }}>Update your account password.</div>
          </div>
          {!showPwForm && (
            <button onClick={() => setShowPwForm(true)} style={{ background:C.surf3, border:`1px solid ${C.border}`, color:C.text, borderRadius:6, padding:"6px 14px", fontSize:11, cursor:"pointer" }}>
              Change
            </button>
          )}
        </div>
        {showPwForm && (
          <>
            {feedback && (
              <div style={{ background:feedback.ok ? C.green+"20" : C.red+"20", border:`1px solid ${feedback.ok ? C.green : C.red}44`, borderRadius:6, padding:"7px 10px", fontSize:11, color:feedback.ok ? C.green : C.red, marginBottom:10 }}>
                {feedback.ok ? "✓" : "✕"} {feedback.msg}
              </div>
            )}
            <div style={{ display:"flex", flexDirection:"column", gap:10 }}>
              <FormRow label="Current Password">
                <input style={inputStyle} type="password" placeholder="••••••••" />
              </FormRow>
              <FormRow label="New Password">
                <input style={inputStyle} type="password" placeholder="••••••••" />
              </FormRow>
              <FormRow label="Confirm New Password">
                <input style={inputStyle} type="password" placeholder="••••••••" />
              </FormRow>
            </div>
            <div style={{ display:"flex", gap:8, marginTop:12 }}>
              <button onClick={handlePwSave} style={{ background:C.green, color:"#000", border:"none", borderRadius:6, padding:"7px 18px", fontSize:11, cursor:"pointer", fontWeight:800 }}>
                Update Password
              </button>
              <button onClick={() => setShowPwForm(false)} style={{ background:"transparent", color:C.muted, border:`1px solid ${C.border}`, borderRadius:6, padding:"7px 14px", fontSize:11, cursor:"pointer" }}>
                Cancel
              </button>
            </div>
          </>
        )}
      </Card>

      {/* ── Danger Zone ── */}
      <Card style={{ border:`1px solid ${C.red}33` }}>
        <div style={{ fontSize:13, fontWeight:700, color:C.red, marginBottom:6 }}>Danger Zone</div>
        <div style={{ display:"flex", justifyContent:"space-between", alignItems:"center" }}>
          <div>
            <div style={{ fontSize:11, color:C.text, fontWeight:600 }}>Log Out</div>
            <div style={{ fontSize:10, color:C.muted }}>End your current session and return to the login screen.</div>
          </div>
          <button style={{ background:C.red+"22", color:C.red, border:`1px solid ${C.red}44`, borderRadius:6, padding:"7px 16px", fontSize:11, cursor:"pointer", fontWeight:700 }}>
            Log Out
          </button>
        </div>
      </Card>
    </div>
  );
}

// ─── MAIN APP ─────────────────────────────────────────────────────────────────
export default function SpendermanUI() {
  const [nav,   setNav]   = useState("dashboard");
  const [theme, setTheme] = useState("DARK");

  const navItems = [
    { id:"dashboard",    icon:"⊞", label:"Dashboard"    },
    { id:"transactions", icon:"↕", label:"Transactions"  },
    { id:"wallets",      icon:"◈", label:"Wallets"       },
    { id:"categories",   icon:"◉", label:"Categories"    },
    { id:"cycles",       icon:"◎", label:"Cycles"        },
    { id:"goals",        icon:"◇", label:"Goals"         },
    { id:"settings",     icon:"⚙", label:"Settings"      },
  ];

  return (
    <div style={{ background:C.bg, minHeight:"100vh", padding:20, fontFamily:"'DM Sans',system-ui,sans-serif", color:C.text }}>
      {/* Header */}
      <div style={{ display:"flex", alignItems:"baseline", gap:12, marginBottom:20 }}>
        <div style={{ fontSize:22, fontWeight:900, color:C.green, letterSpacing:"-0.05em" }}>$penderman</div>
        <div style={{ fontSize:11, color:C.muted }}>UI Mockup · v3</div>
        <div style={{ marginLeft:"auto", fontSize:10, color:C.muted, background:C.surf, border:`1px solid ${C.border}`, borderRadius:6, padding:"3px 10px" }}>
          ✦ Changes: New Dashboard · Wallet/Goal tx targeting · Settings screen
        </div>
      </div>

      {/* App Shell */}
      <div style={{ border:`1px solid ${C.border}`, borderRadius:14, overflow:"hidden", display:"flex", height:"78vh" }}>

        {/* ── Sidebar ── */}
        <div style={{ width:195, background:C.surf, borderRight:`1px solid ${C.border}`, display:"flex", flexDirection:"column", flexShrink:0 }}>
          <div style={{ padding:"14px 16px 12px", borderBottom:`1px solid ${C.border}` }}>
            <div style={{ fontSize:16, fontWeight:900, color:C.green, letterSpacing:"-0.05em" }}>$penderman</div>
            <div style={{ fontSize:9, color:C.muted, marginTop:2 }}>Personal Finance</div>
          </div>

          <div style={{ padding:"8px 0", flex:1, overflowY:"auto" }}>
            {navItems.map(item => (
              <button key={item.id} onClick={() => setNav(item.id)} style={{
                display:"flex", alignItems:"center", gap:10, padding:"7px 16px",
                width:"100%", background:nav===item.id ? C.green+"15" : "transparent",
                border:"none", borderLeft:nav===item.id ? `3px solid ${C.green}` : "3px solid transparent",
                cursor:"pointer", color:nav===item.id ? C.green : C.sub,
                fontSize:12, transition:"all 0.12s",
              }}>
                <span style={{ fontSize:13 }}>{item.icon}</span>{item.label}
              </button>
            ))}
          </div>

          <div style={{ padding:"10px 16px", borderTop:`1px solid ${C.border}`, display:"flex", alignItems:"center", gap:8 }}>
            <div style={{ width:28, height:28, borderRadius:"50%", background:C.purple+"44", display:"flex", alignItems:"center", justifyContent:"center", fontSize:10, color:C.purple, fontWeight:700 }}>AH</div>
            <div>
              <div style={{ fontSize:11, color:C.text }}>Ahmed</div>
              <div style={{ fontSize:9, color:C.muted }}>{theme === "DARK" ? "🌙" : "☀️"} {theme} Mode</div>
            </div>
          </div>
        </div>

        {/* ── Content ── */}
        <div style={{ flex:1, overflowY:"auto", background:C.bg, padding:"16px 18px" }}>
          {nav === "dashboard"    && <DashboardScreen />}
          {nav === "transactions" && <TransactionsScreen />}
          {nav === "wallets"      && <WalletsScreen />}
          {nav === "categories"   && <CategoriesScreen />}
          {nav === "cycles"       && <CyclesScreen />}
          {nav === "goals"        && <GoalsScreen />}
          {nav === "settings"     && <SettingsScreen theme={theme} setTheme={setTheme} />}
        </div>
      </div>
    </div>
  );
}
